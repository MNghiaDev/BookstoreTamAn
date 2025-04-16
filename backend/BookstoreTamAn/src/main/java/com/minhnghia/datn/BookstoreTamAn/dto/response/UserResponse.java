package com.minhnghia.datn.BookstoreTamAn.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String username;
    private String password;
    private String email;
    @JsonProperty("full_name")
    private String fullName;
    private String phone;
    private String address;
    private String role;
    @JsonProperty("created_at")
    private LocalDate createdAt;
    @JsonProperty("modify_by")
    private String modifyBy;
    @JsonProperty("modify_at")
    private LocalDate modifyAt;
}
