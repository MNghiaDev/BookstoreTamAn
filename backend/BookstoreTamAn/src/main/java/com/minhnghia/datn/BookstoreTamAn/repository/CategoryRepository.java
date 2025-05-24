package com.minhnghia.datn.BookstoreTamAn.repository;

import com.minhnghia.datn.BookstoreTamAn.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c.name, COUNT(b) FROM Category c JOIN c.books b GROUP BY c.id")
    List<Object[]> getBookCountByCategory();

    Optional<Category> findByName(String name);


}
