package com.example.quizgame.service;

import com.example.quizgame.model.Question;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    // Hardcoded কঠিন প্রশ্ন ও সঠিক উত্তর
    private final List<Question> questions = new ArrayList<>();

    public QuizService() {
        questions.add(new Question(
                "বাংলাদেশের স্বাধীনতা ঘোষণা হয়েছিল কোন বছর?",
                "১৯৭১",
                "১৯৭২",
                "১৯৬৫",
                "১৯৮১",
                "১৯৭১"));

        questions.add(new Question(
                "বিশ্বের সবচেয়ে বড় মহাসাগর কোনটি?",
                "আটলান্টিক মহাসাগর",
                "ইন্ডিয়ান মহাসাগর",
                "প্রশান্ত মহাসাগর",
                "আর্কটিক মহাসাগর",
                "প্রশান্ত মহাসাগর"));

        // আরও প্রশ্ন যোগ করতে পারো এখানেই
    }

    public List<Question> getAllQuestions() {
        return questions;
    }

    // উত্তর অনুযায়ী স্কোর গণনা
    public int calculateScore(List<String> answers) {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (i < answers.size()) {
                if (questions.get(i).getCorrectAnswer().equals(answers.get(i))) {
                    score += 10;  // প্রতি সঠিক উত্তরের জন্য ১০ পয়েন্ট
                }
            }
        }
        return score;
    }
}
