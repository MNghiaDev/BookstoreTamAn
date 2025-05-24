package com.minhnghia.datn.BookstoreTamAn.controller;

import com.minhnghia.datn.BookstoreTamAn.dto.request.ActiveRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.ApiResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.request.CategoryRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.*;
import com.minhnghia.datn.BookstoreTamAn.service.impl.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

//    @GetMapping("/list")
//    public ApiResponse<List<CategoryResponse>> getAll(){
//        return ApiResponse.<List<CategoryResponse>>builder()
//                .data(categoryService.getAll())
//                .build();
//    }
    @GetMapping("/list")
    public ApiResponse<CategoryListResponse> getAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        PageRequest request = PageRequest.of(
                page,
                size, Sort.by("id").ascending()
        );
        Page<CategoryResponse> categories = categoryService.getAll(request);
        int totalPage = categories.getTotalPages();
        List<CategoryResponse> categoryList = categories.getContent();
        CategoryListResponse categoryListResponse = CategoryListResponse.builder()
                .totalPages(totalPage)
                .categoryResponses(categoryList)
                .build();
        return ApiResponse.<CategoryListResponse>builder()
                .data(categoryListResponse)
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
    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getById(@PathVariable("id") Integer categoryId){
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.findById(categoryId))
                .build();
    }
    @GetMapping("/detail/{name}")
    public ApiResponse<CategoryResponse> getDetail(@PathVariable("name") String name){
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.findByName(name))
                .build();
    }
    @PutMapping("/active/{id}")
    public ApiResponse<CategoryResponse> updateActive(@PathVariable("id") Integer id, @RequestBody ActiveRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .data((categoryService.updateActive(id, request)))
                .build();
    }
}
