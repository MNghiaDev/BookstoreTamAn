package com.minhnghia.datn.BookstoreTamAn.service.impl;

import com.minhnghia.datn.BookstoreTamAn.dto.request.ActiveRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.AuthorRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.AuthorBookCountResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.AuthorResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.TopAuthorResponse;
import com.minhnghia.datn.BookstoreTamAn.exception.AppException;
import com.minhnghia.datn.BookstoreTamAn.exception.ErrorCode;
import com.minhnghia.datn.BookstoreTamAn.mapper.AuthorMapper;
import com.minhnghia.datn.BookstoreTamAn.model.Author;
import com.minhnghia.datn.BookstoreTamAn.model.Book;
import com.minhnghia.datn.BookstoreTamAn.model.OrderDetail;
import com.minhnghia.datn.BookstoreTamAn.repository.AuthorRepository;
import com.minhnghia.datn.BookstoreTamAn.repository.BookRepository;
import com.minhnghia.datn.BookstoreTamAn.repository.OrderDetailRepository;
import com.minhnghia.datn.BookstoreTamAn.service.IAuthorService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final BookRepository bookRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public List<AuthorBookCountResponse> getBookCountByAuthor() {
        List<Object[]> result = authorRepository.getBookCountByAuthor();
        return result.stream()
                .map(row -> new AuthorBookCountResponse((String) row[0], (Long) row[1]))
                .collect(Collectors.toList());
    }
    @Override
    public List<TopAuthorResponse> getTopAuthorsBySelling(int top) {
        Pageable pageable = PageRequest.of(0, top);
        List<Object[]> result = authorRepository.findTopAuthorsBySelling(pageable);
        return result.stream()
                .map(row -> new TopAuthorResponse(
                        (String) row[0],
                        (Long) row[1],
                        (String) row[2] ,
                        (String) row[3]))
                .collect(Collectors.toList());
    }

    @Override
    public Page<AuthorResponse> getAll(PageRequest request) {
        return authorRepository.findAll(request).map(authorMapper::toAuthorResponse);
    }

    @Override
    public AuthorResponse create(AuthorRequest request) {
        Author author = authorMapper.toAuthor(request);
        return authorMapper.toAuthorResponse(authorRepository.save(author));
    }

    private Author findId(Integer id){
        return authorRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
    }

    @Override
    public AuthorResponse update(Integer id, AuthorRequest request) {
        Author author = findId(id);
        if(request.getBio() != null){
            author.setBio(request.getBio());
        }
        if(request.getImageUrl() != null){
            author.setImageUrl(request.getImageUrl());
        }
        author.setModifyBy(request.getModifyBy());
        return authorMapper.toAuthorResponse(authorRepository.save(author));
    }

    @Override
    public Void delete(Integer authorId) {
        Author author = findId(authorId);
        authorRepository.delete(author);
        return null;
    }

    public AuthorResponse getAuthorDetail(Integer id){
        Author author = findId(id);
        return authorMapper.toAuthorResponse(author);
    }

    public AuthorResponse getByName(String name){
        Author author = authorRepository.findByName(name)
                .orElseThrow(()-> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
        return authorMapper.toAuthorResponse(author);
    }
    public AuthorResponse updateActive(Integer id, ActiveRequest request){
        Author author = findId(id);
        author.setActive(request.getActive());
        List<Book> books = bookRepository.findAllByAuthor_Id(author.getId());
        books.forEach(book -> book.setActive(request.getActive()));
        bookRepository.saveAll(books);
        return authorMapper.toAuthorResponse(authorRepository.save(author));
    }
}
