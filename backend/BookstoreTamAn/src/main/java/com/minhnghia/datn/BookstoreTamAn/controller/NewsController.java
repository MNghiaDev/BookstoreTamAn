package com.minhnghia.datn.BookstoreTamAn.controller;

import com.minhnghia.datn.BookstoreTamAn.dto.request.ApiResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.request.NewsCreationRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.NewsUpdateRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.BookListResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.BookResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.NewsListResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.NewsResponse;
import com.minhnghia.datn.BookstoreTamAn.service.impl.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping("list")
    public ApiResponse<NewsListResponse> getAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        PageRequest request = PageRequest.of(
                page,
                size, Sort.by("id").ascending()
        );
        Page<NewsResponse> news = newsService.getAll(request);
        int totalPage = news.getTotalPages();
        List<NewsResponse> newsList = news.getContent();
        NewsListResponse newsListResponse = NewsListResponse.builder()
                .totalPages(totalPage)
                .newsResponses(newsList)
                .build();
        return ApiResponse.<NewsListResponse>builder()
                .data(newsListResponse)
                .build();
    }
    @GetMapping("/top-3")
    public ApiResponse<List<NewsResponse>> getTop3(){
        return ApiResponse.<List<NewsResponse>>builder()
                .data(newsService.find3News())
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ApiResponse<NewsResponse> create(@RequestBody NewsCreationRequest request){
        return ApiResponse.<NewsResponse>builder()
                .data(newsService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ApiResponse<NewsResponse> update(@PathVariable("id") int id, @RequestBody NewsUpdateRequest request){
        return ApiResponse.<NewsResponse>builder()
                .data(newsService.update(id, request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ApiResponse<Void> delete(@PathVariable("id") int id){
        return ApiResponse.<Void>builder()
                .data(newsService.delete(id))
                .build();
    }
}
