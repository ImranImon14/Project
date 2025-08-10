package com.example.quizgame.repository;

import com.example.quizgame.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
    // Student entity এর primary key হচ্ছে String টাইপের id
}
