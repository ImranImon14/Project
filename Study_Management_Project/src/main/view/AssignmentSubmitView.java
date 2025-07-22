package main.view;

import main.controller.AssignmentController;
import main.controller.SubmissionController;
import main.model.Assignment;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AssignmentSubmitView extends JFrame {
    private int studentId;
    private String courseCode;

    private JComboBox<AssignmentItem> assignmentComboBox;
    private JTextField submissionField;

    public AssignmentSubmitView(int studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;

        setTitle("Submit Assignment - " + courseCode);
        setSize(600, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Load assignments for the course
        List<Assignment> assignments = AssignmentController.getAssignmentsByCourse(courseCode);
        if (assignments.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No assignments available for this course.");
            dispose();
            return;
        }

        JLabel selectAssignmentLabel = new JLabel("Select Assignment:");
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        add(selectAssignmentLabel, gbc);

        assignmentComboBox = new JComboBox<>();
        // Populate combo with assignment items (custom toString for display)
        for (Assignment a : assignments) {
            assignmentComboBox.addItem(new AssignmentItem(a.getId(), a.getDescription()));
        }
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        add(assignmentComboBox, gbc);

        JLabel submissionLabel = new JLabel("Enter PDF file path or GitHub link:");
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3;
        add(submissionLabel, gbc);

        submissionField = new JTextField();
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 3;
        add(submissionField, gbc);

        JButton submitButton = new JButton("Submit");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        add(submitButton, gbc);

        JButton backButton = new JButton("Back");
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 1;
        add(backButton, gbc);

        submitButton.addActionListener(e -> {
            AssignmentItem selected = (AssignmentItem) assignmentComboBox.getSelectedItem();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Please select an assignment.");
                return;
            }

            String submission = submissionField.getText().trim();
            if (submission.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the PDF path or GitHub link.");
                return;
            }

            boolean success = SubmissionController.submitAssignment(studentId, selected.getId(), submission);
            if (success) {
                JOptionPane.showMessageDialog(this, "Assignment submitted successfully!");
                new DashboardStudent(studentId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Submission failed. Please try again.");
            }
        });

        backButton.addActionListener(e -> {
            new DashboardStudent(studentId);
            dispose();
        });

        setVisible(true);
    }

    // Helper class for combo box items
    private static class AssignmentItem {
        private final int id;
        private final String description;

        public AssignmentItem(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public String toString() {
            return description;
        }
    }
}
