package com.minhnghia.datn.BookstoreTamAn.dto.response;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsListResponse {
    List<NewsResponse> newsResponses;
    int totalPages;
}
