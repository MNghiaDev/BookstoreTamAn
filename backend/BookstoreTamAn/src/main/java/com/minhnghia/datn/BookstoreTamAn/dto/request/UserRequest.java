package com.minhnghia.datn.BookstoreTamAn.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    private String username;
    private String password;
    private String email;
    @JsonProperty("full_name")
    private String fullName;
    private String phone;
    private String address;
}
