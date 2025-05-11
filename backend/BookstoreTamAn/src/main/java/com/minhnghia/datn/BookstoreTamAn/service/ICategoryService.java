package com.minhnghia.datn.BookstoreTamAn.service;

import com.minhnghia.datn.BookstoreTamAn.dto.request.CategoryRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.CategoryBookCountResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService {

    public List<CategoryResponse> getAll();

    List<CategoryBookCountResponse> getBookCountByCategory();

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(int id, CategoryRequest request);

    Void delete(int id);
}
