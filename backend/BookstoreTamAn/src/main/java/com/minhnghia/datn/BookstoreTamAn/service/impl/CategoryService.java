package com.minhnghia.datn.BookstoreTamAn.service.impl;

import com.minhnghia.datn.BookstoreTamAn.dto.response.CategoryBookCountResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.CategoryResponse;
import com.minhnghia.datn.BookstoreTamAn.mapper.CategoryMapper;
import com.minhnghia.datn.BookstoreTamAn.repository.CategoryRepository;
import com.minhnghia.datn.BookstoreTamAn.service.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponse).toList();
    }

    @Override
    public List<CategoryBookCountResponse> getBookCountByCategory() {
        List<Object[]> results = categoryRepository.getBookCountByCategory();
        return results.stream()
                .map(row -> new CategoryBookCountResponse((String) row[0], (Long) row[1]))
                .collect(Collectors.toList());
    }
}
