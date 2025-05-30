package com.minhnghia.datn.BookstoreTamAn.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "author")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String bio;
    private String imageUrl;
    private boolean active;
    private String createdBy;
    private String modifyBy;
}
