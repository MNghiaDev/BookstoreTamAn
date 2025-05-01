package com.minhnghia.datn.BookstoreTamAn.controller;

import com.minhnghia.datn.BookstoreTamAn.dto.request.ApiResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.request.UserRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.UserListResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.UserResponse;
import com.minhnghia.datn.BookstoreTamAn.service.impl.CartService;
import com.minhnghia.datn.BookstoreTamAn.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/list")
    public ApiResponse<UserListResponse> getAlls(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        PageRequest request = PageRequest.of(
                page,
                size, Sort.by("id").ascending()
        );
        Page<UserResponse> users = userService.getAlls(request);
        int totalPage = users.getTotalPages();
        List<UserResponse> userList = users.getContent();
        UserListResponse userListResponse = UserListResponse.builder()
                .userResponses(userList)
                .totalPages(totalPage)
                .build();
        return ApiResponse.<UserListResponse>builder()
                .data(userListResponse)
                .build();
    }

    @GetMapping("/info/{id}")
    public ApiResponse<UserResponse> getInfo(@PathVariable("id") int id){
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUserById(id))
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<UserResponse> create(@RequestBody UserRequest request){
        return ApiResponse.<UserResponse>builder()
                .data(userService.create(request))
                .build();
    }

    @PostMapping("/update/{id}")
    public ApiResponse<UserResponse> update(@PathVariable("id") int id, @RequestBody UserRequest request){
        return ApiResponse.<UserResponse>builder()
                .data(userService.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") int id){
        userService.delete(id);
        return ApiResponse.<Void>builder().build();
    }
}
