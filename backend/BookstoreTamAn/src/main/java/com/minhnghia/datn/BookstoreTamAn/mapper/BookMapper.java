package com.minhnghia.datn.BookstoreTamAn.mapper;

import com.minhnghia.datn.BookstoreTamAn.dto.response.BookResponse;
import com.minhnghia.datn.BookstoreTamAn.model.Book;
import com.minhnghia.datn.BookstoreTamAn.model.Category;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookMapper {
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
                .illustrationsNote(book.getIllustrationsNote())
                .ISBN10(book.getISBN10())
                .ISBN13(book.getISBN13())
                .promotion(book.getPromotion())
                .promotionEndDate(book.getPromotionEndDate())
                .description(book.getDescription())
                .sellerReview(book.getSellerReview())
                .reviewVideo(book.getReviewVideo())
                .price(book.getPrice())
                .stock(book.getStock())
                .imageUrl(book.getImageUrl())
                .rating(book.getRating())
                .numReviews(book.getNumReviews())
                .selling(book.getSelling())
                .createdBy(book.getCreatedBy())
                .createdAt(book.getCreatedAt())
                .modifyBy(book.getModifyBy())
                .modifyAt(book.getModifyAt())
                .nameAuthor(book.getAuthor().getName())
                .categoryNames(
                        book.getCategories().stream()
                                .map(Category::getName)
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
