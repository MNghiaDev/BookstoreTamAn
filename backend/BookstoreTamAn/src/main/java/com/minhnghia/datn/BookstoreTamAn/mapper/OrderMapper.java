package com.minhnghia.datn.BookstoreTamAn.mapper;

import com.minhnghia.datn.BookstoreTamAn.dto.request.OrderRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.OrderResponse;
import com.minhnghia.datn.BookstoreTamAn.exception.AppException;
import com.minhnghia.datn.BookstoreTamAn.exception.ErrorCode;
import com.minhnghia.datn.BookstoreTamAn.model.Order;
import com.minhnghia.datn.BookstoreTamAn.model.User;
import com.minhnghia.datn.BookstoreTamAn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final UserRepository userRepository;
    public OrderResponse toOrderResponse(Order order){
        return OrderResponse.builder()
                .orderId(order.getId())
                .orderCode(order.getOrderCode())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .userId(order.getUser().getId())
                .orderDate(order.getOrderDate())
                .email(order.getEmail())
                .paymentMethod(order.getPaymentMethod())
                .phone(order.getPhone())
                .shippingAddress(order.getShippingAddress())
                .recipientName(order.getRecipientName())
                .createdBy(order.getCreatedBy())
                .modifyAt(order.getModifyAt())
                .modifyBy(order.getModifyBy())
                .active(order.isActive())
                .paid(order.getPaid())
                .build();
    }

    public Order toOrder(OrderRequest request){
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return Order.builder()
                .orderCode(generateOrderCode())
                .user(user)
                .orderDate(LocalDate.now())
                .status("PENDING")
                .email(request.getEmail())
                .paymentMethod(request.getPaymentMethod())
                .phone(request.getPhone())
                .shippingAddress(request.getShippingAddress())
                .recipientName(request.getRecipientName())
                .totalPrice(0.0)
                .active(true)
                .paid(false)
                .build();
    }
    private String generateOrderCode() {
        String prefix = "ORD";
        String date = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.BASIC_ISO_DATE);
        long timestamp = System.currentTimeMillis();
        return prefix + date + (timestamp % 1000000);
    }
}
