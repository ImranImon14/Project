package main.view;

import javax.swing.*;
import java.awt.*;

public class AssignmentCreateView extends JFrame {

    private JTextField txtCourseCode;
    private JTextField txtAssignmentNumber;
    private JTextArea txtAssignment;
    private JButton btnAddAssignment, btnBack;

    private int teacherId;

    public AssignmentCreateView(int teacherId, String courseCode) {
        this.teacherId = teacherId;

        setTitle("Add Assignment");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(6, 1, 5, 5));

        inputPanel.add(new JLabel("Course Code:"));
        txtCourseCode = new JTextField(courseCode);
        inputPanel.add(txtCourseCode);

        inputPanel.add(new JLabel("Assignment Number (e.g. 1, 2):"));
        txtAssignmentNumber = new JTextField();
        inputPanel.add(txtAssignmentNumber);

        inputPanel.add(new JLabel("Assignment Description:"));
        txtAssignment = new JTextArea(5, 20);
        inputPanel.add(new JScrollPane(txtAssignment));

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAddAssignment = new JButton("Add Assignment");
        btnBack = new JButton("Back");

        buttonPanel.add(btnAddAssignment);
        buttonPanel.add(btnBack);

        add(buttonPanel, BorderLayout.SOUTH);

        btnAddAssignment.addActionListener(e -> addAssignment());
        btnBack.addActionListener(e -> {
            new DashboardTeacher(teacherId);
            dispose();
        });

        setVisible(true);
    }

    private void addAssignment() {
        String courseCode = txtCourseCode.getText().trim();
        String assignmentNumberStr = txtAssignmentNumber.getText().trim();
        String assignmentText = txtAssignment.getText().trim();

        if (courseCode.isEmpty() || assignmentNumberStr.isEmpty() || assignmentText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        int assignmentNumber;
        try {
            assignmentNumber = Integer.parseInt(assignmentNumberStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Assignment number must be an integer!");
            return;
        }

        // Add assignment logic
        boolean added = main.controller.AssignmentController.addAssignment(
                teacherId, courseCode, assignmentText, assignmentNumber);

        if (added) {
            JOptionPane.showMessageDialog(this, "Assignment added successfully!");
            txtAssignment.setText("");
            txtAssignmentNumber.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add assignment. Please check the course code.");
        }
    }
}
