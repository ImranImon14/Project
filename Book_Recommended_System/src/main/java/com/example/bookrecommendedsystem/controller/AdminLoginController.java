package com.example.bookrecommendedsystem.controller;

import com.example.bookrecommendedsystem.model.Admin;
import com.example.bookrecommendedsystem.repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "adminlogin"; // your adminlogin.html page
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String password, Model model, HttpSession session) {
        Admin admin = adminRepository.findFirstByOrderByIdAsc();
        if (admin != null && admin.getPassword().equals(password)) {
            // Set admin role in session
            session.setAttribute("role", "admin");
            return "redirect:/books";  // Redirect admin to books list with full privileges
        } else {
            model.addAttribute("error", "Invalid password.");
            return "adminlogin";  // Show login again with error
        }
    }
}
