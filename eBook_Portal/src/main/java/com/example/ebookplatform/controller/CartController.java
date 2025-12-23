package com.example.ebookplatform.controller;

import com.example.ebookplatform.model.Book;
import com.example.ebookplatform.model.CartItem;
import com.example.ebookplatform.model.User;
import com.example.ebookplatform.service.BookService;
import com.example.ebookplatform.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final BookService bookService;

    public CartController(CartService cartService, BookService bookService) {
        this.cartService = cartService;
        this.bookService = bookService;
    }

    @GetMapping("/add/{bookId}")
    public String addToCart(@PathVariable Long bookId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/login";
        Book book = bookService.getBookById(bookId);
        cartService.addToCart(user, book);
        session.setAttribute("cartSuccess", "✅ Product added successfully!");
        return "redirect:/";
    }

    @GetMapping
    public String showCart(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/login";
        List<CartItem> items = cartService.getCartItems(user);
        model.addAttribute("cartItems", items);
        return "user/cart";
    }

    @GetMapping("/remove/{bookId}")
    public String removeFromCart(@PathVariable Long bookId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user != null) {
            Book book = bookService.getBookById(bookId);
            cartService.removeFromCart(user, book);
        }
        return "redirect:/cart";
    }
}
