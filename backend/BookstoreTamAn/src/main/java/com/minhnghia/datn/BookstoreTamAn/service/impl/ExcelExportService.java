package com.minhnghia.datn.BookstoreTamAn.service.impl;

import com.minhnghia.datn.BookstoreTamAn.dto.request.PurchaseTrendDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExcelExportService {
    public void exportRevenueReport(HttpServletResponse response, List<PurchaseTrendDTO> trendData) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Thống kê doanh thu");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Ngày");
        header.createCell(1).setCellValue("Tổng số sản phẩm đã bán");

        Map<LocalDate, Integer> aggregated = trendData.stream()
                .collect(Collectors.groupingBy(
                        PurchaseTrendDTO::getOrderDate,
                        Collectors.summingInt(PurchaseTrendDTO::getTotalSold)
                ));

        int rowIdx = 1;
        for (Map.Entry<LocalDate, Integer> entry : aggregated.entrySet()) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(entry.getKey().toString());
            row.createCell(1).setCellValue(entry.getValue());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=thong_ke_doanh_thu.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
