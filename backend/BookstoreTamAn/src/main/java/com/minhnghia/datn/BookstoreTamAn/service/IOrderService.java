package com.minhnghia.datn.BookstoreTamAn.service;

import com.minhnghia.datn.BookstoreTamAn.dto.request.OrderRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.OrderUpdateStatusRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.OrderCreationResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IOrderService {

    OrderCreationResponse createOrder(OrderRequest request);

    Page<OrderResponse> getAll(PageRequest request);

    OrderResponse updateStatus(Integer id, OrderUpdateStatusRequest request);

    Void delete(Integer id);
}
