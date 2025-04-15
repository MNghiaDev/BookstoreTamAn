package com.minhnghia.datn.BookstoreTamAn.controller;

import com.minhnghia.datn.BookstoreTamAn.dto.request.ApiResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.BookListResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.BookResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.BookSaleListResponse;
import com.minhnghia.datn.BookstoreTamAn.service.impl.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ApiResponse<BookListResponse> getAll(
        @RequestParam("page") int page,
        @RequestParam("size") int size
    ){
        PageRequest request = PageRequest.of(
                page,
                size, Sort.by("id").ascending()
        );
        Page<BookResponse> books = bookService.getAll(request);
        int totalPage =books.getTotalPages();
        List<BookResponse> bookList =books.getContent();
        BookListResponse bookListResponse = BookListResponse.builder()
                .totalPages(totalPage)
                .productResponses(bookList)
                .build();
        return ApiResponse.<BookListResponse>builder()
                .data(bookListResponse)
                .build();
    }

    @GetMapping("{id}")
    public ApiResponse<BookResponse> getById(@PathVariable("id") int id){
        return ApiResponse.<BookResponse>builder()
                .data(bookService.getById(id))
                .build();
    }

    @GetMapping("/top-selling")
    public ApiResponse<List<BookResponse>> getTopSelling(){
        return ApiResponse.<List<BookResponse>>builder()
                .data(bookService.topSelling())
                .build();
    }

    @GetMapping("/sale")
    public ApiResponse<BookSaleListResponse<BookResponse>> getSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<BookResponse> salesPage = bookService.getSales(PageRequest.of(page, size));
        BookSaleListResponse<BookResponse> response = new BookSaleListResponse<>(
                salesPage.getContent(),
                salesPage.getNumber(),
                salesPage.getSize(),
                salesPage.getTotalElements()
        );
        return ApiResponse.<BookSaleListResponse<BookResponse>>builder()
                .data(response)
                .build();
    }

    @GetMapping("/max-sale")
    public ApiResponse<BookResponse> getMaxSaleBook() {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.getBestSale())
                .build();
    }
    @GetMapping("/publication-date")
    public ApiResponse<List<BookResponse>> getBookByPublicationDate(){
        return ApiResponse.<List<BookResponse>>builder()
                .data(bookService.getBookByPublicationDate())
                .build();
    }
}
