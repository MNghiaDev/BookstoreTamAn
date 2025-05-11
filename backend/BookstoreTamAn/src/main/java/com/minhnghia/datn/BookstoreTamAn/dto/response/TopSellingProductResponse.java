// src/main/java/com/minhnghia/datn/BookstoreTamAn/dto/response/TopSellingProductResponse.java
package com.minhnghia.datn.BookstoreTamAn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopSellingProductResponse {
    private long bookId;
    private String bookTitle;
    private long totalSold;
}
