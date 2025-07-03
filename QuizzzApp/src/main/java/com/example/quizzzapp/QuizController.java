package com.example.quizzzapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuizController {

    @FXML private Label questionLabel;
    @FXML private Label questionNumberLabel;
    @FXML private RadioButton option1, option2, option3, option4;
    @FXML private Button startButton;
    @FXML private Button submitButton;
    @FXML private VBox optionsBox;

    private ToggleGroup optionsGroup = new ToggleGroup();

    private List<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;

    public void initialize() {
        // Assign toggle group to RadioButtons
        option1.setToggleGroup(optionsGroup);
        option2.setToggleGroup(optionsGroup);
        option3.setToggleGroup(optionsGroup);
        option4.setToggleGroup(optionsGroup);
    }

    @FXML
    private void handleStart() {
        startButton.setVisible(false);
        questionLabel.setVisible(true);
        questionNumberLabel.setVisible(true);
        optionsBox.setVisible(true);
        submitButton.setVisible(true);

        loadQuestions();
        currentQuestionIndex = 0;
        score = 0;
        showQuestion();
    }

    private void loadQuestions() {
        questions.clear();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM questions")) {

            while (rs.next()) {
                questions.add(new Question(
                        rs.getString("question"),
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

            questionNumberLabel.setText("Ques " + (currentQuestionIndex + 1) + " of " + questions.size());
        } else {
            showFinalScore();
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("Please select an option before submitting.");
            alert.showAndWait();
        }
    }

    private void showFinalScore() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz Completed");
        alert.setHeaderText("Your Score: " + score + " / " + questions.size());

        ButtonType playAgain = new ButtonType("Play Again");
        ButtonType exit = new ButtonType("Exit");

        alert.getButtonTypes().setAll(playAgain, exit);

        alert.showAndWait().ifPresent(type -> {
            if (type == playAgain) {
                currentQuestionIndex = 0;
                score = 0;
                showQuestion();
            } else {
                // Exit app
                System.exit(0);
            }
        });
    }
}
