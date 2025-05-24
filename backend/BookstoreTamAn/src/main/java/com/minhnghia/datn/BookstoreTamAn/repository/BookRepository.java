package com.minhnghia.datn.BookstoreTamAn.repository;

import com.minhnghia.datn.BookstoreTamAn.model.Author;
import com.minhnghia.datn.BookstoreTamAn.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    SELECT b.id, b.title, b.price, b.rating, b.selling, b.image_url, a.name AS author_name
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

    Optional<Book> findByTitle(String title);

    Page<Book> findAllByCategories_Id(Pageable pageable, Integer categoryId);

    Page<Book> findAllByAuthor_Id(Pageable pageable, Integer authorId);

    List<Book> findAllByAuthor_Id(Integer authorId);

    @Query("SELECT b FROM Book b JOIN b.categories c JOIN b.author a " +
            "WHERE b.title = :title AND a.name = :authorName AND c.name IN :categoryNames")
    List<Book> checkBookExists(@Param("title") String title,
                               @Param("authorName") String authorName,
                               @Param("categoryNames") Set<String> categoryNames);

    @Query("SELECT b FROM Book b WHERE b.price BETWEEN :minPrice AND :maxPrice")
    Page<Book> findByPriceBetween(
            Pageable pageable,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );
    Page<Book> findByTitleContainingIgnoreCaseOrAuthor_NameContainingIgnoreCaseAndPriceBetween(
            String titleKeyword,
            String authorKeyword,
            double minPrice,
            double maxPrice,
            Pageable pageable
    );

}
