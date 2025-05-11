package com.minhnghia.datn.BookstoreTamAn.service.impl;

import com.minhnghia.datn.BookstoreTamAn.dto.request.CategoryRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.CategoryBookCountResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.CategoryResponse;
import com.minhnghia.datn.BookstoreTamAn.exception.AppException;
import com.minhnghia.datn.BookstoreTamAn.exception.ErrorCode;
import com.minhnghia.datn.BookstoreTamAn.mapper.CategoryMapper;
import com.minhnghia.datn.BookstoreTamAn.model.Category;
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

    @Override
    public CategoryResponse create(CategoryRequest request) {
        Category category = categoryMapper.toCategory(request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }
    private Category findId(int id){
        return categoryRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }
    @Override
    public CategoryResponse update(int id, CategoryRequest request) {
        Category category = findId(id);
        if(request.getDescription() != null){
            category.setDescription(category.getDescription());
        }
        category.setModifyBy(request.getModifyBy());
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public Void delete(int id) {
        Category category = findId(id);
        categoryRepository.delete(category);
        return null;
    }
}
