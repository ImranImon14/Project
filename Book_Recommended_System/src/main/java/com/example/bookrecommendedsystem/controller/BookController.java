package com.example.bookrecommendedsystem.controller;

import com.example.bookrecommendedsystem.model.Book;
import com.example.bookrecommendedsystem.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Show all books - available to all (admin/user)
    @GetMapping("/books")
    public String home(Model model, HttpSession session) {
        model.addAttribute("books", bookService.getAllBooks());

        // Pass role (admin/user) to view for controlling UI
        String role = (String) session.getAttribute("role");
        model.addAttribute("role", role != null ? role : "user");

        return "index";
    }

    // View books by genre
    @GetMapping("/genre")
    public String booksByGenre(@RequestParam String genre, Model model, HttpSession session) {
        List<Book> books = bookService.getBooksByGenre(genre);
        model.addAttribute("books", books);
        model.addAttribute("genre", genre);

        String role = (String) session.getAttribute("role");
        model.addAttribute("role", role != null ? role : "user");

        return "genre";
    }

    // Search books
    @GetMapping("/search")
    public String searchBooks(@RequestParam String keyword, Model model, HttpSession session) {
        List<Book> books = bookService.searchBooks(keyword);
        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);

        String role = (String) session.getAttribute("role");
        model.addAttribute("role", role != null ? role : "user");

        return "search";
    }

    // Show add book form - only for admin
    @GetMapping("/books/add")
    public String showAddBookForm(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if (!"admin".equals(role)) {
            return "redirect:/books";
        }

        model.addAttribute("book", new Book());
        return "addBook";
    }

    // Handle add book - only for admin
    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book book, Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"admin".equals(role)) {
            return "redirect:/books";
        }

        Book added = bookService.addBook(book);
        if (added == null) {
            model.addAttribute("error", "Book already exists!");
            return "addBook";
        }

        return "redirect:/books";
    }

    // Delete book - only for admin
    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"admin".equals(role)) {
            return "redirect:/books";
        }

        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
