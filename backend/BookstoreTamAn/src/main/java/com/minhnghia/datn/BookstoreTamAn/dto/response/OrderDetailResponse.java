package com.minhnghia.datn.BookstoreTamAn.dto.response;

import com.minhnghia.datn.BookstoreTamAn.model.Book;
import com.minhnghia.datn.BookstoreTamAn.model.Order;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    private Integer id;
    private Integer quantity;
    private Double unitPrice;
    private String createdBy;
    private String modifyBy;
    private boolean active;
    private String bookTitle;
}
