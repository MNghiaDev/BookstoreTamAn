// src/main/java/com/minhnghia/datn/BookstoreTamAn/dto/response/SalesStatisticsResponse.java
package com.minhnghia.datn.BookstoreTamAn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalesStatisticsResponse {
    private double totalRevenue;
    private int totalOrders;
    private int totalProductsSold;
}
