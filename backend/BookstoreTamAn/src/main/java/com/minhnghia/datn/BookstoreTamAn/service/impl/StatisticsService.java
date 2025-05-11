package com.minhnghia.datn.BookstoreTamAn.service;

import com.minhnghia.datn.BookstoreTamAn.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Map<String, Object> calculateTotalStatistics(LocalDate startDate, LocalDate endDate) {
        return orderRepository.calculateTotalStatistics(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> calculatePurchaseTrend(LocalDate startDate, LocalDate endDate) {
        return orderRepository.calculatePurchaseTrend(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> findTopSellingProducts(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findTopSellingProducts(startDate, endDate);
    }
}
