package main.view;

import main.controller.AssignmentController;
import main.model.Assignment;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AssignmentListView extends JFrame {
    private int userId;
    private String courseCode;
    private boolean isTeacher;

    public AssignmentListView(int userId, String courseCode, boolean isTeacher) {
        this.userId = userId;
        this.courseCode = courseCode;
        this.isTeacher = isTeacher;

        setTitle("Assignments - " + courseCode);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Assignment List for Course: " + courseCode);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        List<Assignment> assignments = AssignmentController.getAssignmentsByCourse(courseCode);

        if (assignments.isEmpty()) {
            add(new JLabel("No assignments found for this course.", SwingConstants.CENTER), BorderLayout.CENTER);
        } else {
            DefaultListModel<String> listModel = new DefaultListModel<>();
            int serial = 1;
            for (Assignment a : assignments) {
                listModel.addElement(serial++ + ". " + a.getDescription());
            }

            JList<String> assignmentList = new JList<>(listModel);
            assignmentList.setFont(new Font("Serif", Font.PLAIN, 14));
            add(new JScrollPane(assignmentList), BorderLayout.CENTER);
        }

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 40));
        add(backButton, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            if (isTeacher) {
                new DashboardTeacher(userId);
            } else {
                new DashboardStudent(userId);
            }
            dispose();
        });

        setVisible(true);
    }
}
