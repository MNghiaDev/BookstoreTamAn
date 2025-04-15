package com.minhnghia.datn.BookstoreTamAn.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "news")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String imageUrl;
    private String createdBy;
    private String modifyBy;
}
