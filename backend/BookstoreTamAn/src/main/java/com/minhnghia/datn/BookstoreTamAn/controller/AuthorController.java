package com.minhnghia.datn.BookstoreTamAn.controller;

import com.minhnghia.datn.BookstoreTamAn.dto.request.ActiveRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.ApiResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.request.AuthorRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.*;
import com.minhnghia.datn.BookstoreTamAn.model.Author;
import com.minhnghia.datn.BookstoreTamAn.service.impl.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list")
    public ApiResponse<AuthorListResponse> getAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        PageRequest request = PageRequest.of(
                page,
                size, Sort.by("id").ascending()
        );
        Page<AuthorResponse> authors = authorService.getAll(request);
        int totalPage = authors.getTotalPages();
        List<AuthorResponse> authorList = authors.getContent();
        AuthorListResponse authorListResponse = AuthorListResponse.builder()
                .totalPages(totalPage)
                .authorResponses(authorList)
                .build();
        return ApiResponse.<AuthorListResponse>builder()
                .data(authorListResponse)
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<AuthorResponse> create(@RequestBody AuthorRequest request){
        return ApiResponse.<AuthorResponse>builder()
                .data(authorService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<AuthorResponse> update(@PathVariable("id") Integer id, @RequestBody AuthorRequest request){
        return ApiResponse.<AuthorResponse>builder()
                .data(authorService.update(id, request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") Integer id){
        return ApiResponse.<Void>builder()
                .data(authorService.delete(id))
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<AuthorResponse> getAuthorDetail(@PathVariable Integer id){
        return ApiResponse.<AuthorResponse>builder()
                .data(authorService.getAuthorDetail(id))
                .build();
    }

    @GetMapping("/detail/{name}")
    public ApiResponse<AuthorResponse> getDetail(@PathVariable("name") String name){
        return ApiResponse.<AuthorResponse>builder()
                .data(authorService.getByName(name))
                .build();
    }
    @PutMapping("/active/{id}")
    public ApiResponse<AuthorResponse> updateActive(@PathVariable("id") Integer id, @RequestBody ActiveRequest request){
        return ApiResponse.<AuthorResponse>builder()
                .data((authorService.updateActive(id, request)))
                .build();
    }
}
