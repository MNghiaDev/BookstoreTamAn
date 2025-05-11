package com.minhnghia.datn.BookstoreTamAn.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateRequest {
    private int bookId;
    private Double promotion;
    private LocalDate promotionEndDate;
    private String sellerReview;
    private String reviewVideo;
    private Double price;
    private Integer stock;
    private String imageUrl;
    private String modifyBy;
}
