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
public class BookCreationRequest {
    private String title;
    private String format;
    private Integer pages;
    private Float width;
    private Float height;
    private Float length;
    private Float weight;
    private Date publicationDate;
    private String publisher;
    private String language;
    private Double promotion;
    private LocalDate promotionEndDate;
    private String description;
    private String sellerReview;
    private String reviewVideo;
    private Double price;
    private Integer stock;
    private String imageUrl;
    private Float rating;
    private Integer selling;
    private String createdBy;

    private int authorId;
    private String nameUser;
    private Set<Integer> categoryId;
}
