package com.minhnghia.datn.BookstoreTamAn.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "book")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String illustrationsNote;
    private String ISBN10;
    private String ISBN13;
    private Double promotion;
    private LocalDate promotionEndDate;
    private String description;
    private String sellerReview;
    private String reviewVideo;
    private Double price;
    private Integer stock;
    private String imageUrl;
    private Float rating;
    private Integer numReviews;
    private Integer selling;
    private String createdBy;
    private String modifyBy;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;
}
