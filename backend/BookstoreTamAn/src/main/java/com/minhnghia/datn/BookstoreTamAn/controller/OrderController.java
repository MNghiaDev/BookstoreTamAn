package com.minhnghia.datn.BookstoreTamAn.controller;

import com.minhnghia.datn.BookstoreTamAn.dto.request.ApiResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.request.OrderRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.OrderUpdateStatusRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.NewsListResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.NewsResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.OrderListResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.OrderResponse;
import com.minhnghia.datn.BookstoreTamAn.service.impl.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ApiResponse<?> createOrder(@RequestBody OrderRequest request) {
        return ApiResponse.builder()
                .message("Đặt hàng thành công")
                .data(orderService.createOrder(request))
                .build();
    }
    @GetMapping("list")
    public ApiResponse<OrderListResponse> getAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        PageRequest request = PageRequest.of(
                page,
                size, Sort.by("id").ascending()
        );
        Page<OrderResponse> order = orderService.getAll(request);
        int totalPage = order.getTotalPages();
        List<OrderResponse> orderList = order.getContent();
        OrderListResponse orderListResponse = OrderListResponse.builder()
                .totalPages(totalPage)
                .orderResponses(orderList)
                .build();
        return ApiResponse.<OrderListResponse>builder()
                .data(orderListResponse)
                .build();
    }

    @PutMapping("/update-status/{id}")
    public ApiResponse<OrderResponse> updateStatus(@PathVariable("id") Integer id, @RequestBody OrderUpdateStatusRequest request){
        return ApiResponse.<OrderResponse>builder()
                .data(orderService.updateStatus(id, request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") Integer id){
        return ApiResponse.<Void>builder()
                .data(orderService.delete(id))
                .build();
    }
}
