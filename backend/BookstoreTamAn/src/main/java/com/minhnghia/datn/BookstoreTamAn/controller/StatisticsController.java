package com.minhnghia.datn.BookstoreTamAn.controller;

import com.minhnghia.datn.BookstoreTamAn.dto.request.PurchaseTrendDTO;
import com.minhnghia.datn.BookstoreTamAn.dto.request.SalesPerMonthDTO;
import com.minhnghia.datn.BookstoreTamAn.repository.OrderRepository;
import com.minhnghia.datn.BookstoreTamAn.service.StatisticsService;
import com.minhnghia.datn.BookstoreTamAn.service.impl.ExcelExportService;
import com.minhnghia.datn.BookstoreTamAn.service.impl.OrderService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;
    private final OrderService orderService;
    private final ExcelExportService excelExportService;

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
    @GetMapping("/export")
    public void exportRevenue(@RequestParam String startDate,
                              @RequestParam String endDate,
                              HttpServletResponse response) throws IOException {

        List<PurchaseTrendDTO> data = orderService.getPurchaseTrend(startDate, endDate);
        excelExportService.exportRevenueReport(response, data);
    }

    private final OrderRepository orderRepository;

    @GetMapping("/sales-per-month")
    public ResponseEntity<List<SalesPerMonthDTO>> getSalesPerMonth(
            @RequestParam(value = "year", defaultValue = "2025") int year) {
        List<SalesPerMonthDTO> result = orderRepository.countBooksSoldPerMonth(year);
        return ResponseEntity.ok(result);
    }
}
