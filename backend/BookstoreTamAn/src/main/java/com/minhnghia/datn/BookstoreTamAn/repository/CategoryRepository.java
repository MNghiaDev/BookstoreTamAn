package com.minhnghia.datn.BookstoreTamAn.repository;

import com.minhnghia.datn.BookstoreTamAn.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c.name, COUNT(b) FROM Category c JOIN c.books b GROUP BY c.id")
    List<Object[]> getBookCountByCategory();


}
