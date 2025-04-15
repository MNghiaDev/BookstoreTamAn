package com.minhnghia.datn.BookstoreTamAn.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minhnghia.datn.BookstoreTamAn.model.Book;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Integer id;

    private String name;
    private String description;
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("modify_by")
    private String modifyBy;
    @JsonProperty("created_at")
    private LocalDate createdAt;
    @JsonProperty("modify_at")
    private LocalDate modifyAt;

    private Set<CategoryBookResponse> books;
}
