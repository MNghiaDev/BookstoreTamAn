package com.minhnghia.datn.BookstoreTamAn.service.impl;

import com.minhnghia.datn.BookstoreTamAn.dto.request.ActiveRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.BookCreationRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.BookUpdateRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.BookResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.PagedResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.TopBookByTopAuthorResponse;
import com.minhnghia.datn.BookstoreTamAn.exception.AppException;
import com.minhnghia.datn.BookstoreTamAn.exception.ErrorCode;
import com.minhnghia.datn.BookstoreTamAn.mapper.BookMapper;
import com.minhnghia.datn.BookstoreTamAn.model.Author;
import com.minhnghia.datn.BookstoreTamAn.model.Book;
import com.minhnghia.datn.BookstoreTamAn.model.Category;
import com.minhnghia.datn.BookstoreTamAn.repository.AuthorRepository;
import com.minhnghia.datn.BookstoreTamAn.repository.BookRepository;
import com.minhnghia.datn.BookstoreTamAn.repository.CategoryRepository;
import com.minhnghia.datn.BookstoreTamAn.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<BookResponse> getAll(PageRequest request) {
        resetExpiredPromotions();
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
                        ((Number) row[2]).doubleValue(),
                        ((Number) row[3]).floatValue(),
                        ((Number) row[4]).intValue(),
                        (String) row[5],
                        (String) row[6]
                ))
                .collect(Collectors.toList());
    }
    public Page<BookResponse> searchBooks(String keyword, Pageable pageable) {
        Page<Book> books = bookRepository.findByTitleContainingIgnoreCaseOrAuthor_NameContainingIgnoreCase(keyword, keyword, pageable);

        return books.map(bookMapper::toBookResponse);
    }

    @Override
    public Page<BookResponse> filterBooksByPrice(PageRequest request, Double minPrice, Double maxPrice) {
        return bookRepository.findByPriceBetween(request, minPrice, maxPrice)
                .map(bookMapper::toBookResponse);
    }
    public PagedResponse<BookResponse> searchAndFilter(String keyword, double minPrice, double maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository
                .findByTitleContainingIgnoreCaseOrAuthor_NameContainingIgnoreCaseAndPriceBetween(
                        keyword, keyword, minPrice, maxPrice, pageable
                );

        List<BookResponse> content = bookPage.map(bookMapper::toBookResponse).getContent();
        return new PagedResponse<>(content, bookPage.getTotalPages());
    }


    public List<String> getSuggestions(String keyword) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCaseOrAuthor_NameContainingIgnoreCase(keyword, keyword);
        return books.stream()
                .map(Book::getTitle)
                .distinct()
                .limit(10)
                .toList();
    }

    public BookResponse create(BookCreationRequest request){
// Kiểm tra tác giả có tồn tại không
        Author author = authorRepository.findByName(request.getAuthorName())
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));

        // Lấy danh sách thể loại
        Set<Category> categories = request.getCategoryName().stream()
                .map(name -> categoryRepository.findByName(name)
                        .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)))
                .collect(Collectors.toSet());

        // Kiểm tra sách đã tồn tại chưa
        List<Book> existingBooks = bookRepository.checkBookExists(request.getTitle(), request.getAuthorName(), request.getCategoryName());
        if (!existingBooks.isEmpty()) {
            throw new AppException(ErrorCode.BOOK_ALREADY_EXISTS);
        }

        // Tạo mới sách
        Book book = bookMapper.toBook(request);
        book.setAuthor(author);
        book.setCategories(categories);
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
    public void resetExpiredPromotions() {
        List<Book> books = bookRepository.findAll();
        LocalDate today = LocalDate.now();

        List<Book> booksToUpdate = books.stream()
                .filter(book -> book.getPromotionEndDate() != null && book.getPromotionEndDate().isBefore(today))
                .peek(book -> {
                    book.setPromotion(0.0);
                    book.setPromotionEndDate(null);
                })
                .collect(Collectors.toList());

        if (!booksToUpdate.isEmpty()) {
            bookRepository.saveAll(booksToUpdate);
        }
    }
    public BookResponse updateActive(int id, ActiveRequest request){
        Book book = getBookById(id);
        book.setActive(request.getActive());
        bookRepository.save(book);
        return bookMapper.toBookResponse(book);
    }
}
