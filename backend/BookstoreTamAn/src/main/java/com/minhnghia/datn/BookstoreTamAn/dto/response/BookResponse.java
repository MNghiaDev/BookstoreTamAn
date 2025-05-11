package com.minhnghia.datn.BookstoreTamAn.dto.response;

import com.minhnghia.datn.BookstoreTamAn.model.Author;
import com.minhnghia.datn.BookstoreTamAn.model.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Integer id;
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
    private LocalDate createdAt;
    private String modifyBy;
    private LocalDate modifyAt;

    private String nameAuthor;
    private Set<String> categoryNames;
}
