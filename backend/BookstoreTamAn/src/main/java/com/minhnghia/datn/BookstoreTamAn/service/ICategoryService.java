package com.minhnghia.datn.BookstoreTamAn.service;

import com.minhnghia.datn.BookstoreTamAn.dto.response.CategoryBookCountResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.CategoryResponse;

import java.util.List;

public interface ICategoryService {

    public List<CategoryResponse> getAll();

    List<CategoryBookCountResponse> getBookCountByCategory();
}
