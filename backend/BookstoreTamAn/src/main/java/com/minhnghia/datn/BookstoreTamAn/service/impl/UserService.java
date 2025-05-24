package com.minhnghia.datn.BookstoreTamAn.service.impl;

import com.minhnghia.datn.BookstoreTamAn.dto.request.ActiveRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.ChangePasswordRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.UserRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.UserResponse;
import com.minhnghia.datn.BookstoreTamAn.exception.AppException;
import com.minhnghia.datn.BookstoreTamAn.exception.ErrorCode;
import com.minhnghia.datn.BookstoreTamAn.mapper.UserMapper;
import com.minhnghia.datn.BookstoreTamAn.model.User;
import com.minhnghia.datn.BookstoreTamAn.repository.UserRepository;
import com.minhnghia.datn.BookstoreTamAn.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw  new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if(userRepository.existsByPhone(request.getPhone())){
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        User user = userMapper.toUser(request);
        if(request.getRole() != null){
            user.setRole(request.getRole());
        }
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public Page<UserResponse> getAlls(PageRequest request) {
        return userRepository.findAll(request).map(userMapper::toUserResponse);
    }

    private User userById(int id){
        return userRepository.findById(id)
                .orElseThrow(() ->new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    @Override
    public UserResponse getUserById(int id) {
        return userMapper.toUserResponse(userById(id));
    }

    @Override
    public UserResponse update(int id, UserRequest request) {
        User userExisting = userById(id);
        if(userExisting == null){
            throw  new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if(request.getUsername() != null){
            userExisting.setUsername(request.getUsername());
        }
        if(request.getPassword() != null){
            userExisting.setPassword(request.getPassword());
        }
        if(request.getEmail() != null){
            userExisting.setEmail(request.getEmail());
        }
        if(request.getFullName() != null){
            userExisting.setFullName(request.getFullName());
        }
        if(request.getPhone() != null){
            userExisting.setPhone(request.getPhone());
        }
        if(request.getAddress() != null){
            userExisting.setAddress(request.getAddress());
        }
        userRepository.save(userExisting);
        return userMapper.toUserResponse(userExisting);
    }

    @Override
    public Void delete(int id) {
        User userExisting = userById(id);
        if(userExisting == null){
            throw  new AppException(ErrorCode.USER_NOT_EXISTED);
        }else{
            userRepository.delete(userExisting);
        }
        return null;
    }
    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    public void changePassword(int id, ChangePasswordRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
    public UserResponse updateActive(Integer id, ActiveRequest request){
        User user = userById(id);
        user.setActive(request.getActive());
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
