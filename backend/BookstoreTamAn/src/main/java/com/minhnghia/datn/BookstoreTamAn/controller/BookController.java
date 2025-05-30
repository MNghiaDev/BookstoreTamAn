package com.minhnghia.datn.BookstoreTamAn.controller;

import com.minhnghia.datn.BookstoreTamAn.dto.request.ActiveRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.ApiResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.request.BookCreationRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.BookUpdateRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.*;
import com.minhnghia.datn.BookstoreTamAn.model.Book;
import com.minhnghia.datn.BookstoreTamAn.service.impl.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/top-books-by-top-authors")
    public ApiResponse<List<TopBookByTopAuthorResponse>> getTopBooksByTopAuthors() {
        return ApiResponse.<List<TopBookByTopAuthorResponse>>builder()
                .data(bookService.getTopBooksFromTopAuthors())
                .build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookResponse>> searchBooks(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookResponse> books = bookService.searchBooks(keyword, pageable);
        return ResponseEntity.ok(books);
    }
    // BookController.java
    @GetMapping("/suggestions")
    public ResponseEntity<?> getSuggestions(@RequestParam String keyword) {
        List<String> suggestions = bookService.getSuggestions(keyword);
        return ResponseEntity.ok(Map.of("data", suggestions));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ApiResponse<BookResponse> create(@RequestBody BookCreationRequest request){
        return ApiResponse.<BookResponse>builder()
                .data(bookService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ApiResponse<BookResponse> update(@PathVariable("id") int id, @RequestBody BookUpdateRequest request){
        return ApiResponse.<BookResponse>builder()
                .data(bookService.update(id, request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") int id){
        bookService.delete(id);
        return ApiResponse.<Void>builder()
                .build();
    }
    @GetMapping("/by-category/{id}")
    public ApiResponse<BookListResponse> getAllByCategory(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @PathVariable("id") int id
    ){
        PageRequest request = PageRequest.of(
                page,
                size, Sort.by("id").ascending()
        );
        Page<BookResponse> books = bookService.getAllByCategory(request, id);
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

    @GetMapping("/by-author/{id}")
    public ApiResponse<BookListResponse> getAllByAuthor(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @PathVariable("id") int id
    ){
        PageRequest request = PageRequest.of(
                page,
                size, Sort.by("id").ascending()
        );
        Page<BookResponse> books = bookService.getAllByAuthor(request, id);
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
    @GetMapping("/filter")
    public ApiResponse<BookListResponse> filterBooksByPrice(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        PageRequest request = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<BookResponse> filteredBooks = bookService.filterBooksByPrice(request, minPrice, maxPrice);

        BookListResponse response = BookListResponse.builder()
                .totalPages(filteredBooks.getTotalPages())
                .productResponses(filteredBooks.getContent())
                .build();

        return ApiResponse.<BookListResponse>builder()
                .data(response)
                .build();
    }
    @GetMapping("/filter/search")
    public ResponseEntity<?> filterByPriceAndKeyword(
            @RequestParam String keyword,
            @RequestParam double minPrice,
            @RequestParam double maxPrice,
            @RequestParam int page,
            @RequestParam int size
    ) {
        PagedResponse<BookResponse> result = bookService.searchAndFilter(keyword, minPrice, maxPrice, page, size);
        return ResponseEntity.ok().body(Map.of("data", result));
    }

    @PutMapping("/active/{id}")
    public ApiResponse<BookResponse> updateActive(@PathVariable("id") Integer id, @RequestBody ActiveRequest request){
        return ApiResponse.<BookResponse>builder()
                .data(bookService.updateActive(id,request))
                .build();
    }

}
