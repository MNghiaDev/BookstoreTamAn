package com.minhnghia.datn.BookstoreTamAn.mapper;

import com.minhnghia.datn.BookstoreTamAn.dto.request.AuthorRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.AuthorResponse;
import com.minhnghia.datn.BookstoreTamAn.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public AuthorResponse toAuthorResponse(Author author){
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .bio(author.getBio())
                .imageUrl(author.getImageUrl())
                .createdBy(author.getCreatedBy())
                .createdAt(author.getCreatedAt())
                .modifyBy(author.getModifyBy())
                .modifyAt(author.getModifyAt())
                .active(author.isActive())
                .build();
    }

    public Author toAuthor(AuthorRequest request){
        return Author.builder()
                .name(request.getName())
                .bio(request.getBio())
                .imageUrl(request.getImageUrl())
                .createdBy(request.getCreatedBy())
                .active(true)
                .build();
    }
}
