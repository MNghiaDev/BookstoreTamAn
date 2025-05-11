package com.minhnghia.datn.BookstoreTamAn.repository;

import com.minhnghia.datn.BookstoreTamAn.model.Book;
import com.minhnghia.datn.BookstoreTamAn.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    List<News> findTop3ByOrderByCreatedAtDesc();
}
