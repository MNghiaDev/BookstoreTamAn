package com.minhnghia.datn.BookstoreTamAn.controller;

import com.minhnghia.datn.BookstoreTamAn.dto.request.*;
import com.minhnghia.datn.BookstoreTamAn.dto.response.AuthenticationResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.IntrospectResponse;
import com.minhnghia.datn.BookstoreTamAn.repository.UserRepository;
import com.minhnghia.datn.BookstoreTamAn.service.impl.AuthenticationService;
import com.minhnghia.datn.BookstoreTamAn.service.impl.OTPService;
import com.minhnghia.datn.BookstoreTamAn.service.impl.UserService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final OTPService otpService;
    private final UserRepository userRepository;

    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.Authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .data(result)
                .build();
    }

    @PostMapping("/inspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .data(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshTokenRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder().data(result).build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Kiểm tra email có tồn tại không
        if (!userRepository.existsByEmail(email)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Email không tồn tại trong hệ thống");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        // Gửi OTP
        otpService.generateAndSendOTP(email);

        Map<String, String> response = new HashMap<>();
        response.put("message", "OTP đã được gửi thành công");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        boolean verified = otpService.verifyOTP(request.getEmail(), request.getOtp());
        if (!verified) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "OTP không đúng hoặc đã hết hạn");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        userService.updatePassword(request.getEmail(), request.getNewPassword());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Đặt lại mật khẩu thành công");
        return ResponseEntity.ok(response);
    }
}
