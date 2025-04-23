package com.minhnghia.datn.BookstoreTamAn.controller;

import com.minhnghia.datn.BookstoreTamAn.dto.request.ApiResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.AuthorBookCountResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.TopAuthorResponse;
import com.minhnghia.datn.BookstoreTamAn.service.impl.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/book-counts")
    public ApiResponse<List<AuthorBookCountResponse>> getBookCountByAuthor(){
        return ApiResponse.<List<AuthorBookCountResponse>>builder()
                .data(authorService.getBookCountByAuthor())
                .build();
    }

    @GetMapping("/top-authors")
    public ApiResponse<List<TopAuthorResponse>> getTopAuthors() {
        return ApiResponse.<List<TopAuthorResponse>>builder()
                .data(authorService.getTopAuthorsBySelling(3))
                .build();
    }

}
