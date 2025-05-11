package com.minhnghia.datn.BookstoreTamAn.service.impl;

import com.minhnghia.datn.BookstoreTamAn.dto.request.NewsCreationRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.NewsUpdateRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.NewsResponse;
import com.minhnghia.datn.BookstoreTamAn.exception.AppException;
import com.minhnghia.datn.BookstoreTamAn.exception.ErrorCode;
import com.minhnghia.datn.BookstoreTamAn.mapper.NewsMapper;
import com.minhnghia.datn.BookstoreTamAn.model.News;
import com.minhnghia.datn.BookstoreTamAn.repository.NewsRepository;
import com.minhnghia.datn.BookstoreTamAn.service.INewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService implements INewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Override
    public Page<NewsResponse> getAll(PageRequest request) {
        return newsRepository.findAll(request).map(newsMapper::toNewsResponse);
    }

    @Override
    public NewsResponse create(NewsCreationRequest request) {
        News news = newsMapper.toNews(request);
        return newsMapper.toNewsResponse(newsRepository.save(news));
    }

    private News findById(int id){
        return newsRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.NEWS_NOT_FOUND));
    }

    @Override
    public NewsResponse update(int id, NewsUpdateRequest request) {
        News news = findById(id);
        if(request.getContent() != null){
            news.setContent(request.getContent());
        }
        if(request.getImageUrl() != null){
            news.setImageUrl(request.getImageUrl());
        }
        news.setModifyBy(request.getModifyBy());
        return newsMapper.toNewsResponse(newsRepository.save(news));
    }

    @Override
    public Void delete(int id) {
        News news = findById(id);
        newsRepository.delete(news);
        return null;
    }

    @Override
    public List<NewsResponse> find3News() {
        return newsRepository.findTop3ByOrderByCreatedAtDesc()
                .stream().map(newsMapper::toNewsResponse).toList();
    }
}
