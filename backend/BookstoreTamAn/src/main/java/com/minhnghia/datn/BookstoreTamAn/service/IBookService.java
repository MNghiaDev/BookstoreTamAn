package com.minhnghia.datn.BookstoreTamAn.service;

import com.minhnghia.datn.BookstoreTamAn.dto.response.BookResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.TopBookByTopAuthorResponse;
import com.minhnghia.datn.BookstoreTamAn.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookService {
    public Page<BookResponse> getAll(PageRequest request);

    public BookResponse getById(int id);

    public List<BookResponse> topSelling();

    public Page<BookResponse> getSales(Pageable pageable);

    public BookResponse getBestSale();

    public List<BookResponse> getBookByPublicationDate();

    List<TopBookByTopAuthorResponse> getTopBooksFromTopAuthors();

    public Page<BookResponse> searchBooks(String keyword, Pageable pageable);

    Page<BookResponse> filterBooksByPrice(PageRequest request, Double minPrice, Double maxPrice);
}
