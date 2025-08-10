package com.example.quizgame.controller;

import com.example.quizgame.model.Question;
import com.example.quizgame.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;

    // প্রথম পেজে প্রশ্ন দেখানো (Student ID নেই, সরাসরি শুরু)
    @GetMapping("/")
    public String showQuiz(Model model) {
        model.addAttribute("questions", quizService.getAllQuestions());
        return "quiz"; // quiz.html
    }

    // উত্তর সাবমিট করা ও স্কোর দেখানো
    @PostMapping("/submit")
    public String submitQuiz(@RequestParam(name = "answers") List<String> answers, Model model) {
        int expectedCount = quizService.getAllQuestions().size();

        if (answers == null || answers.size() != expectedCount) {
            model.addAttribute("error", "অনুগ্রহ করে সব প্রশ্নের উত্তর দিন");
            model.addAttribute("questions", quizService.getAllQuestions());
            return "quiz";
        }

        int score = quizService.calculateScore(answers);
        model.addAttribute("score", score);
        model.addAttribute("total", expectedCount * 10);
        return "result";  // result.html
    }
}
