package com.minhnghia.datn.BookstoreTamAn.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorResponse {
    int id;
    String name;
    String bio;
    String imageUrl;
    String createdBy;
    LocalDate createdAt;
    String modifyBy;
    LocalDate modifyAt;
    Boolean active;
}
