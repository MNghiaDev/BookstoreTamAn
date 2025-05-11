package com.minhnghia.datn.BookstoreTamAn.service;

import com.minhnghia.datn.BookstoreTamAn.dto.request.NewsCreationRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.NewsUpdateRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.BookResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.NewsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface INewsService {
    public Page<NewsResponse> getAll(PageRequest request);

    public NewsResponse create(NewsCreationRequest request);

    public NewsResponse update(int id, NewsUpdateRequest request);

    public Void delete(int id);

    public List<NewsResponse> find3News();
}
