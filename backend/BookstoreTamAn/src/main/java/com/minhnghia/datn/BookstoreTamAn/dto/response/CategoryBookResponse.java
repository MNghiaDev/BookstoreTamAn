package com.minhnghia.datn.BookstoreTamAn.dto.response;

import com.minhnghia.datn.BookstoreTamAn.model.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryBookResponse {
    private Integer id;
    private String title;
    private String imageUrl;
    private Double price;
    private Integer stock;
    private Float rating;
    private String nameAuthor;

}
