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
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        Container c = getContentPane();
        c.setBackground(new Color(255, 239, 213)); // peach color

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 18);

        // Screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width;
        int frameHeight = screenSize.height;

        // Center form block
        int formWidth = 600;
        int formHeight = 600;
        int x = (frameWidth - formWidth) / 2;
        int y = (frameHeight - formHeight) / 2;

        int labelWidth = 220;
        int fieldWidth = 300;
        int height = 35;
        int gap = 50;
        int currentY = y;

        JLabel title = new JLabel("Register to Study Management System");
        title.setFont(new Font("Monaco", Font.BOLD, 24));
        title.setBounds(x-30, currentY - 70, formWidth, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);

        // Name
        addLabel("Name:", x, currentY, labelFont, labelWidth);
        nameField = addField(x + labelWidth + 10, currentY, fieldFont, fieldWidth);
        currentY += gap;

        // Email
        addLabel("Email:", x, currentY, labelFont, labelWidth);
        emailField = addField(x + labelWidth + 10, currentY, fieldFont, fieldWidth);
        currentY += gap;

        // Password
        addLabel("Password:", x, currentY, labelFont, labelWidth);
        passwordField = new JPasswordField();
        passwordField.setBounds(x + labelWidth + 10, currentY, fieldWidth, height);
        passwordField.setFont(fieldFont);
        add(passwordField);
        currentY += gap;

        // Role
        addLabel("Role:", x, currentY, labelFont, labelWidth);
        roleBox = new JComboBox<>(new String[]{"student", "teacher"});
        roleBox.setBounds(x + labelWidth + 10, currentY, fieldWidth, height);
        roleBox.setFont(fieldFont);
        add(roleBox);
        currentY += gap;

        // Student ID
        addLabel("Student ID (only for student):", x, currentY, labelFont, labelWidth);
        studentIdField = addField(x + labelWidth + 10, currentY, fieldFont, fieldWidth);
        currentY += gap;

        // Course Code
        addLabel("Course Code:", x, currentY, labelFont, labelWidth);
        courseCodeField = addField(x + labelWidth + 10, currentY, fieldFont, fieldWidth);
        currentY += gap;

        // Course Name
        addLabel("Course Name:", x, currentY, labelFont, labelWidth);
        courseNameField = addField(x + labelWidth + 10, currentY, fieldFont, fieldWidth);
        currentY += gap + 20;

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(x, currentY, 200, 45);
        registerButton.setFont(new Font("Monaco", Font.BOLD, 18));
        registerButton.setBackground(new Color(40, 167, 69));
        registerButton.setForeground(Color.WHITE);
        add(registerButton);

        // Back button
        JButton backButton = new JButton("Back to Login");
        backButton.setBounds(x + 320, currentY, 200, 45);
        backButton.setFont(new Font("Monaco", Font.BOLD, 18));
        backButton.setBackground(new Color(0, 123, 255));
        backButton.setForeground(Color.WHITE);
        add(backButton);

        // Enable student ID if role is student
        roleBox.addActionListener(e -> {
            boolean isStudent = roleBox.getSelectedItem().toString().equals("student");
            studentIdField.setEnabled(isStudent);
            if (!isStudent) studentIdField.setText("");
        });

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

    private void addLabel(String text, int x, int y, Font font, int width) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setBounds(x, y, width, 30);
        add(label);
    }

    private JTextField addField(int x, int y, Font font, int width) {
        JTextField field = new JTextField();
        field.setFont(font);
        field.setBounds(x, y, width, 35);
        add(field);
        return field;
    }
}
