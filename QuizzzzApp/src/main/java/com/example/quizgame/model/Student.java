package com.example.quizgame.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {
    @Id
    private String id;      // যেমন: IT22010

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
