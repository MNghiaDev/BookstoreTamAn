package com.minhnghia.datn.BookstoreTamAn.controller;

import com.minhnghia.datn.BookstoreTamAn.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getTotalStatistics(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        return ResponseEntity.ok(statisticsService.calculateTotalStatistics(
                LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }

    @GetMapping("/trend")
    public ResponseEntity<List<Map<String, Object>>> getPurchaseTrend(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        return ResponseEntity.ok(statisticsService.calculatePurchaseTrend(
                LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<Map<String, Object>>> getTopSellingProducts(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        return ResponseEntity.ok(statisticsService.findTopSellingProducts(
                LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }
}
