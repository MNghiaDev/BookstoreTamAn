package com.minhnghia.datn.BookstoreTamAn.controller;

import com.minhnghia.datn.BookstoreTamAn.dto.request.ApiResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.request.CategoryRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.CategoryBookCountResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.CategoryResponse;
import com.minhnghia.datn.BookstoreTamAn.service.impl.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/list")
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

    @PostMapping("/create")
    public ApiResponse<CategoryResponse> create(@RequestBody CategoryRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<CategoryResponse> update(@PathVariable("id") Integer id, CategoryRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.update(id, request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") Integer id){
        return ApiResponse.<Void>builder()
                .data(categoryService.delete(id))
                .build();
    }
}
