package com.minhnghia.datn.BookstoreTamAn.service;

import com.minhnghia.datn.BookstoreTamAn.dto.request.UserRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IUserService {
    public UserResponse create(UserRequest request);

    public Page<UserResponse> getAlls(PageRequest request);

    public UserResponse getUserById(int id);
    public UserResponse update(int id, UserRequest request);

    public Void delete(int id);
}
