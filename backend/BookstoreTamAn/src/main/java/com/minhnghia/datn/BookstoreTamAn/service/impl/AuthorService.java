package com.minhnghia.datn.BookstoreTamAn.service.impl;

import com.minhnghia.datn.BookstoreTamAn.dto.response.AuthorBookCountResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.TopAuthorResponse;
import com.minhnghia.datn.BookstoreTamAn.repository.AuthorRepository;
import com.minhnghia.datn.BookstoreTamAn.service.IAuthorService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService {

    private final AuthorRepository authorRepository;

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
                        (String) row[0],      // authorName
                        (Long) row[1],        // totalSold
                        (String) row[2]))     // imageUrl
                .collect(Collectors.toList());
    }

}
