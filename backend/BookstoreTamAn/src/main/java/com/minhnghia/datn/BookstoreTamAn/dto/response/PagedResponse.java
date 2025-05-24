package com.minhnghia.datn.BookstoreTamAn.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PagedResponse<T> {
    private List<T> productResponses;
    private int totalPages;
}
