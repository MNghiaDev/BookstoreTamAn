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

    @Query(value = """
    WITH top_authors AS (
        SELECT b.author_id, SUM(b.selling) as total_sold
        FROM book b
        GROUP BY b.author_id
        ORDER BY total_sold DESC
        LIMIT 3
    )
    SELECT b.id, b.title, b.selling, b.image_url, a.name AS author_name
    FROM book b
    JOIN author a ON b.author_id = a.id
    JOIN top_authors ta ON b.author_id = ta.author_id
    WHERE b.selling = (
        SELECT MAX(b2.selling)
        FROM book b2
        WHERE b2.author_id = b.author_id
    )
    """, nativeQuery = true)
    List<Object[]> findTopSellingBookPerTopAuthor();

    Page<Book> findByTitleContainingIgnoreCaseOrAuthor_NameContainingIgnoreCase(String title, String author, Pageable pageable);

    List<Book> findByTitleContainingIgnoreCaseOrAuthor_NameContainingIgnoreCase(String titleKeyword, String authorKeyword);

}
