package com.minhnghia.datn.BookstoreTamAn.service.impl;

import com.minhnghia.datn.BookstoreTamAn.dto.request.BookCreationRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.BookUpdateRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.BookResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.TopBookByTopAuthorResponse;
import com.minhnghia.datn.BookstoreTamAn.exception.AppException;
import com.minhnghia.datn.BookstoreTamAn.exception.ErrorCode;
import com.minhnghia.datn.BookstoreTamAn.mapper.BookMapper;
import com.minhnghia.datn.BookstoreTamAn.model.Book;
import com.minhnghia.datn.BookstoreTamAn.repository.BookRepository;
import com.minhnghia.datn.BookstoreTamAn.service.IBookService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Page<BookResponse> getAll(PageRequest request) {
        return bookRepository.findAll(request).map(bookMapper::toBookResponse);
    }

    public Page<BookResponse> getAllByCategory(PageRequest request, int categoryId){
        return bookRepository.findAllByCategories_Id(request, categoryId).map(bookMapper::toBookResponse);
    }

    public Page<BookResponse> getAllByAuthor(PageRequest request, int authorId){
        return bookRepository.findAllByAuthor_Id(request, authorId).map(bookMapper::toBookResponse);
    }

    private Book getBookById(int id){
        return bookRepository.findById(id)
                .orElseThrow(() ->new AppException(ErrorCode.BOOK_NOT_FOUND));
    }

    @Override
    public BookResponse getById(int id) {
        Book book = getBookById(id);
        return bookMapper.toBookResponse(book);
    }

    @Override
    public List<BookResponse> topSelling() {
        return bookRepository.findTop6ByOrderBySellingDesc().stream()
                .map(bookMapper::toBookResponse).toList();
    }

    @Override
    public Page<BookResponse> getSales(Pageable pageable) {
        return bookRepository
                .findByPromotionGreaterThanAndPromotionEndDateAfter(pageable,0.0, LocalDate.now())
                .map(bookMapper::toBookResponse);
    }

    @Override
    public BookResponse getBestSale() {
        Book book = bookRepository
                .findTopByPromotionGreaterThanAndPromotionEndDateAfterOrderByPromotionDesc(0.0, LocalDate.now());
        return bookMapper.toBookResponse(book);
    }

    @Override
    public List<BookResponse> getBookByPublicationDate() {
        return bookRepository.findTop3ByOrderByPublicationDateDesc().stream()
                .map(bookMapper::toBookResponse).toList();
    }

    @Override
    public List<TopBookByTopAuthorResponse> getTopBooksFromTopAuthors() {
        List<Object[]> rows = bookRepository.findTopSellingBookPerTopAuthor();
        return rows.stream()
                .map(row -> new TopBookByTopAuthorResponse(
                        (Integer) row[0],
                        (String) row[1],
                        ((Number) row[2]).intValue(),
                        (String) row[3],
                        (String) row[4]
                ))
                .collect(Collectors.toList());
    }
    public Page<BookResponse> searchBooks(String keyword, Pageable pageable) {
        Page<Book> books = bookRepository.findByTitleContainingIgnoreCaseOrAuthor_NameContainingIgnoreCase(keyword, keyword, pageable);

        return books.map(bookMapper::toBookResponse);
    }
    public List<String> getSuggestions(String keyword) {
        // Query tìm các sách có tên hoặc tác giả chứa từ khóa
        List<Book> books = bookRepository.findByTitleContainingIgnoreCaseOrAuthor_NameContainingIgnoreCase(keyword, keyword);

        // Lấy ra danh sách tên sách gợi ý
        return books.stream()
                .map(Book::getTitle)
                .distinct()
                .limit(10)
                .toList();
    }

    public BookResponse create(BookCreationRequest request){
        Book book = bookMapper.toBook(request);
        return bookMapper.toBookResponse(bookRepository.save(book));
    }

    public BookResponse update(int id, BookUpdateRequest request){
        Book book = getBookById(id);
        if(book != null){
            if(request.getPromotion() != null){
                book.setPromotion(request.getPromotion());
            }
            if(request.getPromotionEndDate() != null){
                book.setPromotionEndDate(request.getPromotionEndDate());
            }
            if(request.getSellerReview() != null){
                book.setSellerReview(request.getSellerReview());
            }
            if(request.getPrice() != null){
                book.setPrice(request.getPrice());
            }
            if(request.getReviewVideo() != null){
                book.setReviewVideo(request.getReviewVideo());
            }
            if(request.getStock() != null){
                book.setStock(request.getStock());
            }
            if(request.getImageUrl() != null){
                book.setImageUrl(request.getImageUrl());
            }
            book.setModifyBy(request.getModifyBy());
        }
        return bookMapper.toBookResponse(bookRepository.save(book));
    }

    public void delete(int id){
        Book book = getBookById(id);
        bookRepository.delete(book);
    }
}
