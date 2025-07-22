package main.view;

import main.controller.CourseController;

import javax.swing.*;
import java.awt.*;

public class DashboardStudent extends JFrame {
    private int studentId;
    private JTextField courseCodeField;
    private JLabel courseNameLabel;

    public DashboardStudent(int studentId) {
        this.studentId = studentId;

        setTitle("Student Dashboard");
        setSize(200, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 1, 10, 10));

        add(new JLabel("Enter Course Code:"));

        courseCodeField = new JTextField();
        add(courseCodeField);

        JButton loadCourseButton = new JButton("Load Course");
        add(loadCourseButton);

        courseNameLabel = new JLabel("Course Name: ");
        add(courseNameLabel);

        JButton viewAssignmentsButton = new JButton("View Assignments");
        JButton submitAssignmentButton = new JButton("Submit Assignment");
        JButton viewAttendanceButton = new JButton("View Attendance");
        JButton logoutButton = new JButton("Logout");

        add(viewAssignmentsButton);
        add(submitAssignmentButton);
        add(viewAttendanceButton);
        add(logoutButton);

        // Load course name
        loadCourseButton.addActionListener(e -> {
            String courseCode = courseCodeField.getText().trim();
            String courseName = CourseController.getCourseName(courseCode);
            courseNameLabel.setText("Course Name: " + courseName);
        });

        // View Assignments
        viewAssignmentsButton.addActionListener(e -> {
            String courseCode = courseCodeField.getText().trim();
            if (!courseCode.isEmpty()) {
                new AssignmentListView(studentId, courseCode, false); // false for student
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a course code!");
            }
        });

        // Submit Assignment
        submitAssignmentButton.addActionListener(e -> {
            String courseCode = courseCodeField.getText().trim();
            if (!courseCode.isEmpty()) {
                new AssignmentSubmitView(studentId, courseCode);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a course code!");
            }
        });

        // View Attendance (Corrected to 2 params)
        viewAttendanceButton.addActionListener(e -> {
            String courseCode = courseCodeField.getText().trim();
            if (!courseCode.isEmpty()) {
                new StudentAttendanceView(studentId, courseCode);  // Only 2 parameters here
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a course code!");
            }
        });

        // Logout
        logoutButton.addActionListener(e -> {
            new LoginView();
            dispose();
        });

        setVisible(true);
    }
}
