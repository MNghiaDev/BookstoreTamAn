package com.minhnghia.datn.BookstoreTamAn.repository;

import com.minhnghia.datn.BookstoreTamAn.model.Author;
import com.minhnghia.datn.BookstoreTamAn.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("SELECT a.name, COUNT(b) FROM Author a JOIN Book b ON b.author = a GROUP BY a.id")
    List <Object[]> getBookCountByAuthor();

    @Query("SELECT a.name, SUM(b.selling) as totalSold, a.imageUrl, a.bio " +
            "FROM Author a JOIN Book b ON b.author = a " +
            "GROUP BY a.id, a.name, a.imageUrl, a.bio " +
            "ORDER BY totalSold DESC")
    List<Object[]> findTopAuthorsBySelling(Pageable pageable);

//    Page<Author> findAll(Pageable pageable);


    Optional<Author> findByName(String name);

    @Query("SELECT b FROM Book b WHERE b.author.id = :authorId")
    List<Book> findBooksByAuthorId(@Param("authorId") Integer authorId);
}
