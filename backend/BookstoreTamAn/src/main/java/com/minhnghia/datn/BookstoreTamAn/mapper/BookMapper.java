package com.minhnghia.datn.BookstoreTamAn.mapper;

import com.minhnghia.datn.BookstoreTamAn.dto.request.BookCreationRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.BookResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.CategoryResponse;
import com.minhnghia.datn.BookstoreTamAn.exception.AppException;
import com.minhnghia.datn.BookstoreTamAn.exception.ErrorCode;
import com.minhnghia.datn.BookstoreTamAn.model.Author;
import com.minhnghia.datn.BookstoreTamAn.model.Book;
import com.minhnghia.datn.BookstoreTamAn.model.Category;
import com.minhnghia.datn.BookstoreTamAn.repository.AuthorRepository;
import com.minhnghia.datn.BookstoreTamAn.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private  final AuthorRepository authorRepository;
    private  final CategoryRepository categoryRepository;
    public BookResponse toBookResponse(Book book){
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .format(book.getFormat())
                .pages(book.getPages())
                .width(book.getWidth())
                .height(book.getHeight())
                .length(book.getLength())
                .weight(book.getWeight())
                .publicationDate(book.getPublicationDate())
                .publisher(book.getPublisher())
                .language(book.getLanguage())
                .promotion(book.getPromotion())
                .promotionEndDate(book.getPromotionEndDate())
                .description(book.getDescription())
                .sellerReview(book.getSellerReview())
                .reviewVideo(book.getReviewVideo())
                .price(book.getPrice())
                .stock(book.getStock())
                .imageUrl(book.getImageUrl())
                .rating(book.getRating())
                .selling(book.getSelling())
                .createdBy(book.getCreatedBy())
                .createdAt(book.getCreatedAt())
                .modifyBy(book.getModifyBy())
                .modifyAt(book.getModifyAt())
                .nameAuthor(book.getAuthor().getName())
                .authorId(book.getAuthor().getId())
                .categoryNames(toCategoryResponses(book.getCategories()))
                .active(book.isActive())
                .build();
    }
    private Set<CategoryResponse> toCategoryResponses(Set<Category> categories) {
        return categories.stream()
                .map(category -> CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .collect(Collectors.toSet());
    }
    private Author getAuthor(int authorId){
        return  authorRepository.findById(authorId)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
    }

    private Set<Category> getCategory(Set<Integer> id){
        return new HashSet<>(categoryRepository.findAllById(id));
    }
    public Book toBook(BookCreationRequest request){
        return Book.builder()
                .title(request.getTitle())
                .format(request.getFormat())
                .pages(request.getPages())
                .width(request.getWidth())
                .height(request.getHeight())
                .length(request.getLength())
                .weight(request.getWeight())
                .publicationDate(request.getPublicationDate())
                .publisher(request.getPublisher())
                .language(request.getLanguage())
                .promotion(request.getPromotion())
                .promotionEndDate(request.getPromotionEndDate())
                .description(request.getDescription())
                .sellerReview(request.getSellerReview())
                .reviewVideo(request.getReviewVideo())
                .price(request.getPrice())
                .stock(request.getStock())
                .selling(request.getSelling())
                .imageUrl(request.getImageUrl())
                .createdBy(request.getCreatedBy())
                .active(true)
                .build();
    }
}
