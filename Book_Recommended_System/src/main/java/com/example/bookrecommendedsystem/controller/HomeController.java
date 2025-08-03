package com.example.bookrecommendedsystem.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    // Entry page - lets user pick admin login or user enter
    @GetMapping("/")    //'/' means local host 8080
    public String entryPage() {
        return "entry";  // entry.html with buttons for Admin Login & User Enter
    }

    // Handle user entry (no password)
    @PostMapping("/user/enter")
    public String enterAsUser(HttpSession session) {
        // Set role to "user" in session
        session.setAttribute("role", "user");
        return "redirect:/books";  // Redirect user to books list page
    }

    // Optional: logout handler to clear session and return to entry page
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  //remove data from session
        return "redirect:/";  // Back to entry page
    }
}

