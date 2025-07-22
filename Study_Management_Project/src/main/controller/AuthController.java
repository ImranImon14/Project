package main.controller;

import main.model.DBConnection;
import main.utils.PasswordUtil;
import main.utils.Validator;
import main.view.DashboardStudent;
import main.view.DashboardTeacher;
import main.view.LoginView;

import javax.swing.*;
import java.sql.*;

public class AuthController {

    // LOGIN
    public static void login(String email, String password, JFrame frame) {
        if (!Validator.isValidEmail(email) || !Validator.isValidField(password)) {
            JOptionPane.showMessageDialog(frame, "Invalid email or password format.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                String role = rs.getString("role");
                int userId = rs.getInt("id");

                String hashedInput = PasswordUtil.hashPassword(password);
                if (hashedInput.equals(storedHash)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");

                    if ("teacher".equals(role)) {
                        new DashboardTeacher(userId);
                    } else {
                        new DashboardStudent(userId);
                    }
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect password.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "User not found.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Database error: " + e.getMessage());
        }
    }

    // REGISTER
    public static void register(String name, String email, String password, String role,
                                String studentId, String courseCode, String courseName, JFrame frame) {
        if (!Validator.isValidEmail(email) || !Validator.isValidField(password) || !Validator.isValidField(name)) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please check your fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Insert course if not exists
            String insertCourse = "INSERT IGNORE INTO courses (course_code, course_name) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertCourse)) {
                ps.setString(1, courseCode);
                ps.setString(2, courseName);
                ps.executeUpdate();
            }

            // Get course ID
            int courseId = -1;
            try (PreparedStatement ps = conn.prepareStatement("SELECT id FROM courses WHERE course_code = ?")) {
                ps.setString(1, courseCode);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    courseId = rs.getInt("id");
                }
            }

            if (courseId == -1) throw new SQLException("Course not found.");

            // Insert user
            String insertUser = "INSERT INTO users (name, email, password, role, student_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, PasswordUtil.hashPassword(password));
                ps.setString(4, role);
                ps.setString(5, role.equals("student") ? studentId : null);
                ps.executeUpdate();

                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);

                    // Insert enrollment
                    String enrollSQL = role.equals("student")
                            ? "INSERT INTO student_courses (student_id, course_id) VALUES (?, ?)"
                            : "INSERT INTO teacher_courses (teacher_id, course_id) VALUES (?, ?)";

                    try (PreparedStatement psEnroll = conn.prepareStatement(enrollSQL)) {
                        psEnroll.setInt(1, userId);
                        psEnroll.setInt(2, courseId);
                        psEnroll.executeUpdate();
                    }
                }
            }

            conn.commit();
            JOptionPane.showMessageDialog(frame, "Registration successful!");
            new LoginView();
            frame.dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Registration failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
