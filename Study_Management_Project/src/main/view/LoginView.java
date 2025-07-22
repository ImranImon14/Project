package main.view;

import main.controller.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginView() {
        setTitle("Login - Study Management System");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0; gbc.gridy = 0;
        add(emailLabel, gbc);

        emailField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(loginButton, gbc);

        JButton registerButton = new JButton("Register");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(registerButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                AuthController.login(email, password, LoginView.this);
            }
        });

        registerButton.addActionListener(e -> {
            new RegisterView();
            dispose();
        });

        setVisible(true);
    }
}
