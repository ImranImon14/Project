package com.example.quizgame.repository;

import com.example.quizgame.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    // এখানে Long হচ্ছে Score entity-র primary key টাইপ (id)
}
