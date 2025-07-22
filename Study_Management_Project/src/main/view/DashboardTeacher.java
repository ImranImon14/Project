package main.view;

import main.controller.CourseController;

import javax.swing.*;
import java.awt.*;

public class DashboardTeacher extends JFrame {
    private int teacherId;
    private JTextField courseCodeField;
    private JLabel courseNameLabel;

    public DashboardTeacher(int teacherId) {
        this.teacherId = teacherId;

        setTitle("Teacher Dashboard");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(10, 1, 10, 10));

        add(new JLabel("Enter Course Code:"));

        courseCodeField = new JTextField();
        add(courseCodeField);

        JButton loadCourseButton = new JButton("Load Course");
        add(loadCourseButton);

        courseNameLabel = new JLabel("Course Name: ");
        add(courseNameLabel);

        JButton addAssignmentButton = new JButton("Add Assignment");
        JButton viewAssignmentsButton = new JButton("View Assignments");
        JButton viewSubmissionsButton = new JButton("Submitted Assignments");
        JButton takeAttendanceButton = new JButton("Take Attendance");
        JButton logoutButton = new JButton("Logout");

        add(addAssignmentButton);
        add(viewAssignmentsButton);
        add(viewSubmissionsButton);
        add(takeAttendanceButton);
        add(logoutButton);

        // Load course name
        loadCourseButton.addActionListener(e -> {
            String courseCode = courseCodeField.getText().trim();
            if (courseCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a course code!");
                return;
            }

            String courseName = CourseController.getCourseName(courseCode);
            if (courseName != null) {
                courseNameLabel.setText("Course Name: " + courseName);
            } else {
                courseNameLabel.setText("Course not found!");
            }
        });

        addAssignmentButton.addActionListener(e -> {
            String courseCode = courseCodeField.getText().trim();
            if (!courseCode.isEmpty()) {
                new AssignmentCreateView(teacherId, courseCode);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a course code!");
            }
        });

        viewAssignmentsButton.addActionListener(e -> {
            String courseCode = courseCodeField.getText().trim();
            if (!courseCode.isEmpty()) {
                new AssignmentListView(teacherId, courseCode, true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a course code!");
            }
        });

        viewSubmissionsButton.addActionListener(e -> {
            String courseCode = courseCodeField.getText().trim();
            if (!courseCode.isEmpty()) {
                new SubmissionListView(teacherId, courseCode);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a course code!");
            }
        });

        // ✅ FIXED: use TakeAttendanceView instead of StudentAttendanceView
        takeAttendanceButton.addActionListener(e -> {
            String courseCode = courseCodeField.getText().trim();
            if (!courseCode.isEmpty()) {
                new TakeAttendanceView(teacherId, courseCode); // ✅ Correct view
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a course code!");
            }
        });

        logoutButton.addActionListener(e -> {
            new LoginView();
            dispose();
        });

        setVisible(true);
    }
}
