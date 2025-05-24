package com.minhnghia.datn.BookstoreTamAn.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @NotBlank
    @Email
    private String email;

    @JsonProperty("full_name")
    @NotBlank
    private String fullName;

    @NotBlank
    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private String address;

    @NotBlank
    private String role;
}
