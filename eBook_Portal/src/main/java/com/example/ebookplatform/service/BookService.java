package com.example.ebookplatform.service;

import com.example.ebookplatform.model.Book;
import com.example.ebookplatform.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // 🔹 সব বই
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // 🔹 Genre অনুযায়ী বই
    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenreIgnoreCase(genre);
    }

    // 🔹 Name বা Author অনুযায়ী search
    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByNameContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
    }

    // 🔹 Name বা Author + Genre অনুযায়ী search
    public List<Book> searchBooksByGenre(String keyword, String genre) {
        return bookRepository.findByGenreIgnoreCaseAndNameContainingIgnoreCaseOrGenreIgnoreCaseAndAuthorContainingIgnoreCase(
                genre, keyword, genre, keyword
        );
    }

    // 🔹 Id অনুযায়ী বই
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
}
