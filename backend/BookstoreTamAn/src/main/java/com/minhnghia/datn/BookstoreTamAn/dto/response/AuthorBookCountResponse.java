package com.minhnghia.datn.BookstoreTamAn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorBookCountResponse {
    private String authorName;
    private Long bookCount;

}
