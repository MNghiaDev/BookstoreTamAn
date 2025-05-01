package com.minhnghia.datn.BookstoreTamAn.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    private int cartItemId;
    private int bookId;
    private String bookTitle;
    private String bookImage;
    private int quantity;
    private double pricePerUnit;
}
