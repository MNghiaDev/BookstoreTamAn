package com.minhnghia.datn.BookstoreTamAn.controller;

import com.minhnghia.datn.BookstoreTamAn.dto.request.AddToCartRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.ApiResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.request.UpdateCartRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.CartItemResponse;
import com.minhnghia.datn.BookstoreTamAn.model.Cart;
import com.minhnghia.datn.BookstoreTamAn.service.impl.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ApiResponse<CartItemResponse> addToCart(@RequestParam int userId, @RequestBody AddToCartRequest request){
        return ApiResponse.<CartItemResponse>builder()
                .message("Thêm vào giỏ hàng thành công")
                .data(cartService.addToCart(userId, request))
                .build();
    }

    @GetMapping("/view")
    public ApiResponse<List<CartItemResponse>> viewCart(@RequestParam int userId){
        return ApiResponse.<List<CartItemResponse>>builder()
                .data(cartService.viewCart(userId))
                .build();
    }
    @PutMapping("/update")
    public ApiResponse<Void> updateCartItem(@RequestBody UpdateCartRequest request){
        cartService.updateCartItem(request);
        return ApiResponse.<Void>builder()
                .message("Cập nhật thành công")
                .build();
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ApiResponse<Void> removeCartItem(@PathVariable("cartItemId") int cartItemId){
        cartService.removeCartItem(cartItemId);
        return ApiResponse.<Void>builder()
                .message("Xóa sản phẩm khỏi giỏ thành công")
                .build();
    }
    @DeleteMapping("/clear")
    public ApiResponse<Void> clearCart(@RequestParam int userId) {
        cartService.clearCart(userId);
        return ApiResponse.<Void>builder()
                .message("Đã xóa toàn bộ giỏ hàng")
                .build();
    }
    @GetMapping("/total")
    public ApiResponse<Double> getTotalPrice(@RequestParam int userId) {
        return ApiResponse.<Double>builder()
                .data(cartService.calculateTotalPrice(userId))
                .build();
    }
}
