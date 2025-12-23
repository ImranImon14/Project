package com.example.ebookplatform.repository;

import com.example.ebookplatform.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByGenreIgnoreCase(String genre);
    List<Book> findByNameContainingIgnoreCaseOrAuthorContainingIgnoreCase(String name, String author);
    List<Book> findByGenreIgnoreCaseAndNameContainingIgnoreCaseOrGenreIgnoreCaseAndAuthorContainingIgnoreCase(
            String genre1, String name, String genre2, String author
    );
}
