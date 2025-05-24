package com.minhnghia.datn.BookstoreTamAn.dto.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String otp;
    private String newPassword;
}
