package com.example.ebookplatform.controller;

import com.example.ebookplatform.model.Book;
import com.example.ebookplatform.model.User;
import com.example.ebookplatform.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String search,
            Model model,
            HttpSession session) {

        List<Book> books;

        if ((search != null && !search.isEmpty()) && (genre != null && !genre.isEmpty())) {
            books = bookService.searchBooksByGenre(search, genre);
        } else if (search != null && !search.isEmpty()) {
            books = bookService.searchBooks(search);
        } else if (genre != null && !genre.isEmpty()) {
            books = bookService.getBooksByGenre(genre);
        } else {
            books = bookService.getAllBooks();
        }

        model.addAttribute("books", books);
        model.addAttribute("selectedGenre", genre);
        model.addAttribute("search", search);

        // Logged-in user info
        User user = (User) session.getAttribute("user");
        model.addAttribute("loggedInUser", user);

        // Cart success message
        String cartSuccess = (String) session.getAttribute("cartSuccess");
        if(cartSuccess != null){
            model.addAttribute("cartSuccess", cartSuccess);
            session.removeAttribute("cartSuccess");
        }

        return "user/home";
    }
}
