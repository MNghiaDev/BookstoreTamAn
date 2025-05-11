package com.minhnghia.datn.BookstoreTamAn.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsCreationRequest {
    String title;
    String content;
    @JsonProperty(namespace = "image_url")
    String imageUrl;
    @JsonProperty(namespace = "created_by")
    String createdBy;
}
