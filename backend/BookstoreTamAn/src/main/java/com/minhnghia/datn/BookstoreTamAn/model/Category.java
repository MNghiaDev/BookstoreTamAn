package com.minhnghia.datn.BookstoreTamAn.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private String createdBy;
    private String modifyBy;

    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book> books;
}
