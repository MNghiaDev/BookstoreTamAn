package com.minhnghia.datn.BookstoreTamAn.mapper;

import com.minhnghia.datn.BookstoreTamAn.dto.request.CategoryRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.CategoryBookResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.CategoryResponse;
import com.minhnghia.datn.BookstoreTamAn.model.Category;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    public CategoryResponse toCategoryResponse(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getName())
                .createdBy(category.getCreatedBy())
                .createdAt(category.getCreatedAt())
                .modifyBy(category.getModifyBy())
                .modifyAt(category.getModifyAt())
                .books(category.getBooks().stream()
                        .map(book -> new CategoryBookResponse(
                                book.getId(),
                                book.getTitle(),
                                book.getImageUrl(),
                                book.getPrice(),
                                book.getStock(),
                                book.getRating(),
                                book.getAuthor().getName() // Lấy tên tác giả
                        ))
                        .collect(Collectors.toSet()))
                .build();
    }

    public Category toCategory(CategoryRequest request){
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdBy(request.getCreatedBy())
                .build();
    }
}
