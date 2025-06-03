package com.minhnghia.datn.BookstoreTamAn.repository;

import com.minhnghia.datn.BookstoreTamAn.dto.request.SalesPerMonthDTO;
import com.minhnghia.datn.BookstoreTamAn.dto.response.TopSellingProductResponse;
import com.minhnghia.datn.BookstoreTamAn.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o.orderDate AS orderDate, SUM(od.quantity) AS totalSold " +
            "FROM Order o JOIN OrderDetail od ON o.id = od.order.id " +
            "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY o.orderDate " +
            "ORDER BY o.orderDate ASC")
    List<Map<String, Object>> calculatePurchaseTrend(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT od.book.title AS bookTitle, SUM(od.quantity) AS totalSold " +
            "FROM OrderDetail od " +
            "WHERE od.order.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY od.book.title " +
            "ORDER BY totalSold DESC")
    List<Map<String, Object>> findTopSellingProducts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(o.id) AS totalOrders, SUM(o.totalPrice) AS totalRevenue, SUM(od.quantity) AS totalProductsSold " +
            "FROM Order o JOIN OrderDetail od ON o.id = od.order.id " +
            "WHERE o.orderDate BETWEEN :startDate AND :endDate")
    Map<String, Object> calculateTotalStatistics(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query("SELECT o.orderDate, SUM(od.quantity) " +
            "FROM OrderDetail od JOIN od.order o " +
            "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY o.orderDate " +
            "ORDER BY o.orderDate")
    List<Object[]> calculatePurchaseTrends(@Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);
    Page<Order> findByStatus(String status, Pageable pageable);

    Page<Order> findByUserId(Integer userId, Pageable pageable);

    @Query("""
    SELECT new com.minhnghia.datn.BookstoreTamAn.dto.request.SalesPerMonthDTO(
            MONTH(o.orderDate),
            SUM(od.quantity)
        )
        FROM Order o
        JOIN o.orderDetails od
        WHERE YEAR(o.orderDate) = :year
        GROUP BY MONTH(o.orderDate)
        ORDER BY MONTH(o.orderDate)
    """)
    List<SalesPerMonthDTO> countBooksSoldPerMonth(@Param("year") int year);
}
