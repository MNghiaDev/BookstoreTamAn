package com.minhnghia.datn.BookstoreTamAn.mapper;

import com.minhnghia.datn.BookstoreTamAn.dto.request.NewsCreationRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.NewsUpdateRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.NewsResponse;
import com.minhnghia.datn.BookstoreTamAn.model.News;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {
    public NewsResponse toNewsResponse(News news){
        return NewsResponse.builder()
                .id(news.getId())
                .title(news.getTitle())
                .content(news.getContent())
                .imageUrl(news.getImageUrl())
                .createdBy(news.getCreatedBy())
                .createdAt(news.getCreatedAt())
                .modifyBy(news.getModifyBy())
                .modifyAt(news.getModifyAt())
                .build();
    }

    public News toNews(NewsCreationRequest request){
        return News.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .createdBy(request.getCreatedBy())
                .build();
    }

    public News toNewsUpdate(NewsUpdateRequest request){
        return News.builder()
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .modifyBy(request.getModifyBy())
                .build();
    }
}
