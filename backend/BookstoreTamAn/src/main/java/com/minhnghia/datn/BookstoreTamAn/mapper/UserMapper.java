package com.minhnghia.datn.BookstoreTamAn.mapper;

import com.minhnghia.datn.BookstoreTamAn.dto.request.UserRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.UserResponse;
import com.minhnghia.datn.BookstoreTamAn.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserResponse toUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .modifyBy(user.getModifyBy())
                .modifyAt(user.getModifyAt())
                .active(user.isActive())
                .build();
    }

    public User toUser(UserRequest request){
        return User.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .address(request.getAddress())
                .phone(request.getPhone())
                .active(true)
                .role("customer")
                .createdAt(LocalDate.now())
                .build();
    }
}
