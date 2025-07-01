module com.example.quizzzapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.quizzzapp to javafx.fxml;
    exports com.example.quizzzapp;
}