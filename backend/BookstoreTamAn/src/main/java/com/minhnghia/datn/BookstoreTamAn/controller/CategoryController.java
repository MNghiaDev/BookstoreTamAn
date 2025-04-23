package com.minhnghia.datn.BookstoreTamAn.controller;

import com.minhnghia.datn.BookstoreTamAn.dto.request.ApiResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.CategoryBookCountResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.CategoryResponse;
import com.minhnghia.datn.BookstoreTamAn.service.impl.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAll(){
        return ApiResponse.<List<CategoryResponse>>builder()
                .data(categoryService.getAll())
                .build();
    }

    @GetMapping("/book-counts")
    public ApiResponse<List<CategoryBookCountResponse>> getBookCountByCategory() {
        return ApiResponse.<List<CategoryBookCountResponse>>builder()
                .data(categoryService.getBookCountByCategory())
                .build();
    }
}
