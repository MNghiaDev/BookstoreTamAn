package com.minhnghia.datn.BookstoreTamAn.controller;

import com.minhnghia.datn.BookstoreTamAn.configuration.VnPayConfig;
import com.minhnghia.datn.BookstoreTamAn.dto.request.OrderRequest;
import com.minhnghia.datn.BookstoreTamAn.model.Order;
import com.minhnghia.datn.BookstoreTamAn.repository.OrderRepository;
import com.minhnghia.datn.BookstoreTamAn.service.impl.EmailService;
import com.minhnghia.datn.BookstoreTamAn.service.impl.OrderService;
import com.minhnghia.datn.BookstoreTamAn.service.impl.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
    private final VnPayConfig vnPayConfig;
    private final OrderService orderService;
    private final VnPayService vnPayService;
    private final OrderRepository orderRepository;
    private final EmailService emailService;

    @GetMapping("/create-payment")
    public ResponseEntity<?> createPayment(@RequestParam int amount, @RequestParam String orderInfo) throws Exception {
        String vnp_ReturnUrl = vnPayConfig.getReturnUrl(); // từ file cấu hình
        String vnp_TmnCode = vnPayConfig.getTmnCode();
        String vnp_HashSecret = vnPayConfig.getSecretKey();
        String vnp_PayUrl = vnPayConfig.getPayUrl();

        // Tạo map các tham số VNPAY
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", String.valueOf(System.currentTimeMillis()));

        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", "127.0.0.1");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Etc/GMT+7"));

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

// Tạo CreateDate từ Calendar
        String createDate = formatter.format(calendar.getTime());
        vnp_Params.put("vnp_CreateDate", createDate);

// Tạo ExpireDate từ Calendar +15 phút
        calendar.add(Calendar.MINUTE, 15);
        String expireDate = formatter.format(calendar.getTime());
        vnp_Params.put("vnp_ExpireDate", expireDate);

// Debug
        System.out.println("CreateDate = " + createDate);
        System.out.println("ExpireDate = " + expireDate);
        System.out.println("System timezone = " + TimeZone.getDefault().getID());
        log.info("CreateDate = ", createDate);
        log.info("ExpireDate = ", expireDate);
        log.info("System timezone = ", TimeZone.getDefault().getID());

        // Sắp xếp và tạo chuỗi hash
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String field : fieldNames) {
            String value = vnp_Params.get(field);
            hashData.append(field).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
            query.append(URLEncoder.encode(field, StandardCharsets.US_ASCII)).append('=')
                    .append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
        }
        hashData.setLength(hashData.length() - 1);
        query.setLength(query.length() - 1);

        String secureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(secureHash);
        String paymentUrl = vnp_PayUrl + "?" + query.toString();
        return ResponseEntity.ok(Map.of("paymentUrl", paymentUrl));
    }
    public String hmacSHA512(String key, String data) throws Exception {
        Mac hmac = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmac.init(secretKey);
        byte[] bytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder hash = new StringBuilder();
        for (byte b : bytes) {
            hash.append(String.format("%02x", b));
        }
        return hash.toString();
    }

    @GetMapping("/ipn")
    public ResponseEntity<?> handleIpn(HttpServletRequest request) throws Exception {
        Map<String, String> fields = new HashMap<>();
        request.getParameterMap().forEach((key, values) -> fields.put(key, values[0]));

        String secureHash = fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        // Tạo lại hash từ fields
        List<String> sortedKeys = new ArrayList<>(fields.keySet());
        Collections.sort(sortedKeys);
        StringBuilder hashData = new StringBuilder();
        for (String key : sortedKeys) {
            hashData.append(key).append('=').append(URLEncoder.encode(fields.get(key), StandardCharsets.US_ASCII)).append('&');
        }
        hashData.setLength(hashData.length() - 1);

        String myHash = hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
        if (!myHash.equals(secureHash)) {
            return ResponseEntity.ok(Map.of("RspCode", "97", "Message", "Checksum không hợp lệ"));
        }

        String responseCode = fields.get("vnp_ResponseCode");
        String txnRef = fields.get("vnp_TxnRef");

        // TODO: Kiểm tra txnRef có tồn tại và có trạng thái chờ thanh toán không
        // TODO: Nếu hợp lệ thì cập nhật trạng thái đơn hàng

        if ("00".equals(responseCode)) {
            Order order = orderRepository.findById(Integer.parseInt(txnRef))
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

            if (!"processing".equals(order.getStatus())) {
                order.setStatus("processing");
                orderRepository.save(order);
                emailService.sendOrderConfirmation(
                        order.getEmail(),
                        "Xác nhận đơn hàng - Tâm An Store",
                        "<h3>Chào " + order.getRecipientName() + ",</h3>"
                                + "<p>Đơn hàng của bạn đã được thanh toán thành công.</p>"
                );
            }
            return ResponseEntity.ok(Map.of("RspCode", "00", "Message", "Xác nhận thành công"));
        }
        else {
            return ResponseEntity.ok(Map.of("RspCode", "00", "Message", "Giao dịch thất bại"));
        }
    }
    @GetMapping("/return")
    public RedirectView handleReturnUrl(HttpServletRequest request) throws Exception {
        Map<String, String> fields = new HashMap<>();
        request.getParameterMap().forEach((key, values) -> fields.put(key, values[0]));

        String secureHash = fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        List<String> sortedKeys = new ArrayList<>(fields.keySet());
        Collections.sort(sortedKeys);
        StringBuilder hashData = new StringBuilder();
        for (String key : sortedKeys) {
            hashData.append(key).append('=').append(URLEncoder.encode(fields.get(key), StandardCharsets.US_ASCII)).append('&');
        }
        hashData.setLength(hashData.length() - 1);

        String myHash = hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
        String status = (!myHash.equals(secureHash)) ? "invalid" : fields.get("vnp_ResponseCode");

        // Redirect về frontend Angular
        String redirectUrl = "http://localhost:4200/payments/payment-callback?status=" + status;
        return new RedirectView(redirectUrl);
    }
    @PostMapping("/vnpay-order")
    public ResponseEntity<?> createVnpayOrder(@RequestBody OrderRequest orderRequest) throws Exception {
        // 1. Lưu đơn hàng tạm thời vào DB
        Order order = orderService.savePendingOrder(orderRequest);

        // 2. Tạo URL thanh toán VNPAY
        double amount = order.getTotalPrice(); // tổng tiền đơn hàng
        String orderInfo = "Thanh toan don hang: " + order.getId();

        String vnpUrl = vnPayService.createPaymentUrl(order.getId(), amount, orderInfo);
        return ResponseEntity.ok(Map.of("paymentUrl", vnpUrl));
    }
    @PutMapping("/confirm-success/{orderId}")
    public ResponseEntity<?> confirmOrderSuccess(@PathVariable Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
        order.setStatus("processing");
        order.setPaid(true);
        orderRepository.save(order);

        emailService.sendOrderConfirmation(
                order.getEmail(),
                "Xác nhận đơn hàng - Tâm An Store",
                "<h3>Chào " + order.getRecipientName() + ",</h3>"
                        + "<p>Đơn hàng của bạn đã được thanh toán thành công.</p>"
        );

        return ResponseEntity.ok(Map.of("message", "Cập nhật đơn hàng thành công"));
    }

}
