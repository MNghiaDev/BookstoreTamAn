package com.minhnghia.datn.BookstoreTamAn.repository;

import com.minhnghia.datn.BookstoreTamAn.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findTop6ByOrderBySellingDesc();

    Page<Book> findByPromotionGreaterThanAndPromotionEndDateAfter(Pageable pageable, Double promotion, LocalDate date);

    Book findTopByPromotionGreaterThanAndPromotionEndDateAfterOrderByPromotionDesc(Double promotion, LocalDate date);

    List<Book> findTop3ByOrderByPublicationDateDesc();
}
