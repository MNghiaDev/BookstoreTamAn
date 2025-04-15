package com.minhnghia.datn.BookstoreTamAn.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "news_comment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsComment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String comment;
    private String modifyBy;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
