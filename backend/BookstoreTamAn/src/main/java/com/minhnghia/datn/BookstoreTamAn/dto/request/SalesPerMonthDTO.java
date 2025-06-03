package com.minhnghia.datn.BookstoreTamAn.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesPerMonthDTO {
    private int month;
    private long totalQuantity;

    public SalesPerMonthDTO(int month, long totalQuantity) {
        this.month = month;
        this.totalQuantity = totalQuantity;
    }
}
