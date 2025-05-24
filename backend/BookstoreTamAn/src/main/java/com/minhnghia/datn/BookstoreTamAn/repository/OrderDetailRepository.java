package com.minhnghia.datn.BookstoreTamAn.repository;

import com.minhnghia.datn.BookstoreTamAn.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> findAllByOrderId(Integer orderId);
}
