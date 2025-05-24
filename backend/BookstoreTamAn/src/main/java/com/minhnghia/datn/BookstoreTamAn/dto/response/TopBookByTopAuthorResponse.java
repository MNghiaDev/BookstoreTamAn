package com.minhnghia.datn.BookstoreTamAn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopBookByTopAuthorResponse {
    private Integer bookId;
    private String title;
    private Double price;
    private Float rating;
    private Integer selling;
    private String imageUrl;
    private String authorName;
}
