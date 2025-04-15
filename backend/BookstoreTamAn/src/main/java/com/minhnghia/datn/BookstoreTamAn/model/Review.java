package com.minhnghia.datn.BookstoreTamAn.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "review")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer rating;
    private String comment;
    private String createdBy;
    private String modifyBy;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
