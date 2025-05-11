package com.minhnghia.datn.BookstoreTamAn.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsResponse {
    int id;
    String title;
    String content;
    @JsonProperty(namespace = "image_url")
    String imageUrl;
    @JsonProperty(namespace = "created_by")
    String createdBy;
    @JsonProperty(namespace = "created_at")
    LocalDate createdAt;
    @JsonProperty(namespace = "modify_by")
    String modifyBy;
    @JsonProperty(namespace = "modify_at")
    LocalDate modifyAt;
}
