package com.minhnghia.datn.BookstoreTamAn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestItem {
    private Integer bookId;
    private Integer quantity;
}
