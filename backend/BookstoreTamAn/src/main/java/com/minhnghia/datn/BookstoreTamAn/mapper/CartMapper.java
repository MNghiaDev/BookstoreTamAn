package com.minhnghia.datn.BookstoreTamAn.mapper;

import com.minhnghia.datn.BookstoreTamAn.dto.response.CartItemResponse;
import com.minhnghia.datn.BookstoreTamAn.model.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {
    public CartItemResponse toCartItemResponse(CartItem cartItem){
        return CartItemResponse.builder()
                .cartItemId(cartItem.getId())
                .bookId(cartItem.getBook().getId())
                .bookTitle(cartItem.getBook().getTitle())
                .bookImage(cartItem.getBook().getImageUrl())
                .quantity(cartItem.getQuantity())
                .pricePerUnit(cartItem.getBook().getPrice())
                .build();
    }
}
