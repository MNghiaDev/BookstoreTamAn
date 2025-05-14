package com.minhnghia.datn.BookstoreTamAn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseTrendDTO {
    private LocalDate orderDate;
    private int totalSold;
}
