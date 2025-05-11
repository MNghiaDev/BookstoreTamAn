package com.minhnghia.datn.BookstoreTamAn.service;

import com.minhnghia.datn.BookstoreTamAn.dto.request.AuthorRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.AuthorBookCountResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.AuthorResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.TopAuthorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAuthorService {

    List<AuthorBookCountResponse> getBookCountByAuthor();

    List<TopAuthorResponse> getTopAuthorsBySelling(int top);

    Page<AuthorResponse> getAll(PageRequest request);

    AuthorResponse create(AuthorRequest request);

    AuthorResponse update(Integer id, AuthorRequest request);

    Void delete(Integer id);
}
