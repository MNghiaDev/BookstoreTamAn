package com.minhnghia.datn.BookstoreTamAn.controller;

import com.cloudinary.Api;
import com.minhnghia.datn.BookstoreTamAn.dto.request.*;
import com.minhnghia.datn.BookstoreTamAn.dto.response.*;
import com.minhnghia.datn.BookstoreTamAn.model.OrderDetail;
import com.minhnghia.datn.BookstoreTamAn.repository.OrderRepository;
import com.minhnghia.datn.BookstoreTamAn.service.impl.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    @GetMapping("/list")
    public ApiResponse<OrderListResponse> getAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "status", required = false) String status
    ) {
        PageRequest request = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<OrderResponse> order;

        if (status != null && !status.isEmpty()) {
            order = orderService.getByStatus(status, request);
        } else {
            order = orderService.getAll(request);
        }

        OrderListResponse response = OrderListResponse.builder()
                .totalPages(order.getTotalPages())
                .orderResponses(order.getContent())
                .build();

        return ApiResponse.<OrderListResponse>builder()
                .data(response)
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
    @PutMapping("/active/{id}")
    public ApiResponse<OrderResponse> updateActive(@PathVariable("id") Integer id, @RequestBody ActiveRequest request){
        return ApiResponse.<OrderResponse>builder()
                .data((orderService.updateActive(id, request)))
                .build();
    }

    @GetMapping("/my-orders")
    public ApiResponse<OrderListResponse> getOrdersByUser(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userIdLong = jwt.getClaim("user");
        Integer userId = userIdLong.intValue();
        PageRequest request = PageRequest.of(page, size, Sort.by("orderDate").descending());

        Page<OrderResponse> orderPage = orderService.getByUserId(userId, request);

        OrderListResponse response = OrderListResponse.builder()
                .totalPages(orderPage.getTotalPages())
                .orderResponses(orderPage.getContent())
                .build();

        return ApiResponse.<OrderListResponse>builder()
                .data(response)
                .build();
    }
    @GetMapping("/my-orders/{order_id}")
    public ApiResponse<List<OrderDetailResponse>> getAllByOrderId(@PathVariable("order_id") Integer orderId){
        return ApiResponse.<List<OrderDetailResponse>>builder()
                .data(orderService.getAllByOrderId(orderId))
                .build();
    }

    @GetMapping("/confirm-received/{order_id}")
    public ApiResponse<OrderResponse> confirmReceived(@PathVariable("order_id") Integer orderId){
        return ApiResponse.<OrderResponse>builder()
                .data(orderService.confirmReceived(orderId))
                .build();
    }

    @GetMapping("/canceled-order/{order_id}")
    public ApiResponse<OrderResponse> canceledOrder(@PathVariable("order_id") Integer orderId){
        return ApiResponse.<OrderResponse>builder()
                .data(orderService.cancelOrder(orderId))
                .build();
    }




}
