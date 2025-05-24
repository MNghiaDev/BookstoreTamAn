package com.minhnghia.datn.BookstoreTamAn.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Integer orderId;
    String orderCode;
    Double totalPrice;
    String status;
    Integer userId;
    LocalDate orderDate;
    String email;
    String paymentMethod;
    String phone;
    String shippingAddress;
    String recipientName;
    String createdBy;
    String modifyBy;
    LocalDate modifyAt;
    Boolean active;
    Boolean paid;
}
