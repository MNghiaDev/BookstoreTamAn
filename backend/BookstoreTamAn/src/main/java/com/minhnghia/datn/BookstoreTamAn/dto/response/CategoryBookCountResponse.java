package com.minhnghia.datn.BookstoreTamAn.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryBookCountResponse {
    private String categoryName;
    private Long bookCount;
}
