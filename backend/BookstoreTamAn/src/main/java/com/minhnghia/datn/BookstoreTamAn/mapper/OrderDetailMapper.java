package com.minhnghia.datn.BookstoreTamAn.mapper;

import com.minhnghia.datn.BookstoreTamAn.dto.response.OrderDetailResponse;
import com.minhnghia.datn.BookstoreTamAn.model.Order;
import com.minhnghia.datn.BookstoreTamAn.model.OrderDetail;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailMapper {

    public OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail){
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .bookTitle(orderDetail.getBook().getTitle())
                .unitPrice(orderDetail.getUnitPrice())
                .quantity(orderDetail.getQuantity())
                .createdBy(orderDetail.getCreatedBy())
                .build();
    }
}
