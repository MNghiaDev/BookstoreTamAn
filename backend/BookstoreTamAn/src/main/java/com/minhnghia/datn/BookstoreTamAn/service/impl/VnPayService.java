package com.minhnghia.datn.BookstoreTamAn.service.impl;

import com.minhnghia.datn.BookstoreTamAn.configuration.VnPayConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class VnPayService {
    private final VnPayConfig vnPayConfig;

    public String createPaymentUrl(Integer orderId, double amount, String orderInfo) throws Exception {
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnPayConfig.getTmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf((long) (amount * 100)));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", orderId.toString());
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getReturnUrl() + "?orderId=" + orderId);
        vnp_Params.put("vnp_IpAddr", "127.0.0.1");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Etc/GMT+7"));

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

// Tạo CreateDate từ Calendar
        String createDate = formatter.format(calendar.getTime());
        vnp_Params.put("vnp_CreateDate", createDate);

// Tạo ExpireDate từ Calendar +15 phút
        calendar.add(Calendar.MINUTE, 1500);
        String expireDate = formatter.format(calendar.getTime());
        vnp_Params.put("vnp_ExpireDate", expireDate);


        System.out.println("CreateDate = " + createDate);
        System.out.println("ExpireDate = " + expireDate);
        System.out.println("System timezone = " + TimeZone.getDefault().getID());

        List<String> keys = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(keys);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String key : keys) {
            String value = vnp_Params.get(key);
            hashData.append(key).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
            query.append(URLEncoder.encode(key, StandardCharsets.US_ASCII)).append('=')
                    .append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
        }

        hashData.setLength(hashData.length() - 1);
        query.setLength(query.length() - 1);

        String secureHash = hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
        query.append("&vnp_SecureHash=").append(secureHash);

        return vnPayConfig.getPayUrl() + "?" + query.toString();
    }

    private String hmacSHA512(String key, String data) throws Exception {
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
}
