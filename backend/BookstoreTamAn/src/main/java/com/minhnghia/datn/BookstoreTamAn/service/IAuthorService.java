package com.minhnghia.datn.BookstoreTamAn.service;

import com.minhnghia.datn.BookstoreTamAn.dto.response.AuthorBookCountResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.TopAuthorResponse;

import java.util.List;

public interface IAuthorService {

    List<AuthorBookCountResponse> getBookCountByAuthor();

    List<TopAuthorResponse> getTopAuthorsBySelling(int top);

}
