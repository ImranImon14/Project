package com.example.quizzzapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuizController {

    @FXML private Label questionLabel;
    @FXML private RadioButton option1, option2, option3, option4;

    private ToggleGroup optionsGroup = new ToggleGroup();  // manage toggle group here

    private List<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;

    public void initialize() {
        // Assign toggle group to RadioButtons
        option1.setToggleGroup(optionsGroup);
        option2.setToggleGroup(optionsGroup);
        option3.setToggleGroup(optionsGroup);
        option4.setToggleGroup(optionsGroup);

        loadQuestions();
        showQuestion();
    }

    private void loadQuestions() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM questions")) {

            while (rs.next()) {
                String qText = rs.getString("question");
                System.out.println("Loaded question: " + qText);  // Debug print

                questions.add(new Question(
                        qText,
                        rs.getString("option1"),
                        rs.getString("option2"),
                        rs.getString("option3"),
                        rs.getString("option4"),
                        rs.getString("answer")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question q = questions.get(currentQuestionIndex);
            questionLabel.setText(q.question);
            option1.setText(q.option1);
            option2.setText(q.option2);
            option3.setText(q.option3);
            option4.setText(q.option4);
            optionsGroup.selectToggle(null); // Clear selection
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Quiz Completed");
            alert.setHeaderText("Your Score: " + score + "/" + questions.size());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSubmit() {
        RadioButton selected = (RadioButton) optionsGroup.getSelectedToggle();
        if (selected != null) {
            String answer = selected.getText();
            if (answer.equals(questions.get(currentQuestionIndex).answer)) {
                score++;
            }
            currentQuestionIndex++;
            showQuestion();
        } else {
            // Optional: prompt user to select an answer before submitting
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("Please select an option before submitting.");
            alert.showAndWait();
        }
    }
}
