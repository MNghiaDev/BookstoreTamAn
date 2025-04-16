package com.minhnghia.datn.BookstoreTamAn.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserListResponse {
    @JsonProperty("user_responses")
    List<UserResponse> userResponses;
    @JsonProperty("total_pages")
    int totalPages;
}
