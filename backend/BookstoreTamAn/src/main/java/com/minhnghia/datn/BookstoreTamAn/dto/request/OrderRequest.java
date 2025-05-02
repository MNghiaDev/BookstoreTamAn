package com.minhnghia.datn.BookstoreTamAn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Integer userId;
    private String email;
    private String paymentMethod;
    private String phone;
    private String shippingAddress;
    private String recipientName;
    private List<OrderRequestItem> items;
}
