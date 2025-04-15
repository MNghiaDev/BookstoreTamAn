package com.minhnghia.datn.BookstoreTamAn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "book_category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book_Category {
    @Id
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Id
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
