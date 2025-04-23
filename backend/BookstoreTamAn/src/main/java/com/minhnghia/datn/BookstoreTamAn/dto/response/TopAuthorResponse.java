package com.minhnghia.datn.BookstoreTamAn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopAuthorResponse {
    private String authorName;
    private Long totalSold;
    private String imageUrl;
}
