package main.view;

import main.controller.AuthController;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {
    private JTextField nameField, emailField, studentIdField, courseCodeField, courseNameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;

    public RegisterView() {
        setTitle("Register - Study Management System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);
        nameField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 0;
        add(nameField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Email:"), gbc);
        emailField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1;
        add(emailField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField();
        gbc.gridx = 1; gbc.gridy = 2;
        add(passwordField, gbc);

        // Role
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Role:"), gbc);
        roleBox = new JComboBox<>(new String[]{"student", "teacher"});
        gbc.gridx = 1; gbc.gridy = 3;
        add(roleBox, gbc);

        // Student ID (only enabled if student)
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Student ID (only for student):"), gbc);
        studentIdField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 4;
        add(studentIdField, gbc);

        // Course Code
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Course Code:"), gbc);
        courseCodeField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 5;
        add(courseCodeField, gbc);

        // Course Name
        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("Course Name:"), gbc);
        courseNameField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 6;
        add(courseNameField, gbc);

        // Buttons
        JButton registerButton = new JButton("Register");
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        add(registerButton, gbc);

        JButton backButton = new JButton("Back to Login");
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        add(backButton, gbc);

        // Enable/disable Student ID based on role selection
        roleBox.addActionListener(e -> {
            boolean isStudent = "student".equals(roleBox.getSelectedItem().toString());
            studentIdField.setEnabled(isStudent);
            if (!isStudent) studentIdField.setText("");
        });
        studentIdField.setEnabled(true); // default for "student"

        registerButton.addActionListener(e -> {
            AuthController.register(
                    nameField.getText().trim(),
                    emailField.getText().trim(),
                    new String(passwordField.getPassword()).trim(),
                    roleBox.getSelectedItem().toString(),
                    studentIdField.getText().trim(),
                    courseCodeField.getText().trim(),
                    courseNameField.getText().trim(),
                    RegisterView.this
            );
        });

        backButton.addActionListener(e -> {
            new LoginView();
            dispose();
        });

        setVisible(true);
    }
}
