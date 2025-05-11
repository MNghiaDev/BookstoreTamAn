package com.minhnghia.datn.BookstoreTamAn.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    String name;
    String description;
    String createdBy;
    String modifyBy;
}
