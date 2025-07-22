package main.view;

import main.model.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubmissionListView extends JFrame {
    private int teacherId;
    private String courseCode;

    private JComboBox<AssignmentItem> assignmentComboBox;
    private DefaultTableModel tableModel;
    private JTable submissionTable;

    private List<AssignmentItem> assignmentsList = new ArrayList<>();

    public SubmissionListView(int teacherId, String courseCode) {
        this.teacherId = teacherId;
        this.courseCode = courseCode;

        setTitle("Submitted Assignments for " + courseCode);
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Create combo box panel
        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboPanel.setBorder(BorderFactory.createTitledBorder("Select Assignment"));

        assignmentComboBox = new JComboBox<>();
        comboPanel.add(assignmentComboBox);
        add(comboPanel, BorderLayout.NORTH);

        String[] columns = {"Student ID", "Name", "Submission Link", "Submitted At"};
        tableModel = new DefaultTableModel(columns, 0);
        submissionTable = new JTable(tableModel);
        submissionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        submissionTable.setCellSelectionEnabled(true);
        submissionTable.setFont(new Font("Serif", Font.PLAIN, 14));

        // Right-click copy menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem copyItem = new JMenuItem("Copy");
        popupMenu.add(copyItem);

        submissionTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int row = submissionTable.rowAtPoint(point);
                int column = submissionTable.columnAtPoint(point);
                submissionTable.setRowSelectionInterval(row, row);
                submissionTable.setColumnSelectionInterval(column, column);

                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(submissionTable, e.getX(), e.getY());
                }
            }
        });

        copyItem.addActionListener(e -> {
            int row = submissionTable.getSelectedRow();
            int col = submissionTable.getSelectedColumn();
            if (row >= 0 && col >= 0) {
                Object value = submissionTable.getValueAt(row, col);
                if (value != null) {
                    StringSelection stringSelection = new StringSelection(value.toString());
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(submissionTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        add(backButton, BorderLayout.SOUTH);
        backButton.addActionListener(e -> {
            new DashboardTeacher(teacherId);
            dispose();
        });

        // Load assignments into combo box
        loadAssignments();

        // Listen for assignment selection changes
        assignmentComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                AssignmentItem selected = (AssignmentItem) assignmentComboBox.getSelectedItem();
                if (selected != null) {
                    loadSubmissions(selected.id);
                }
            }
        });

        setVisible(true);
    }

    private void loadAssignments() {
        String sql = "SELECT id, assignment_number, assignment_text FROM assignments " +
                "WHERE course_id = (SELECT id FROM courses WHERE course_code = ?) " +
                "ORDER BY assignment_number ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, courseCode);
            ResultSet rs = ps.executeQuery();

            assignmentsList.clear();
            assignmentComboBox.removeAllItems();

            while (rs.next()) {
                int assignmentId = rs.getInt("id");
                int assignmentNumber = rs.getInt("assignment_number");
                String description = rs.getString("assignment_text");

                AssignmentItem item = new AssignmentItem(assignmentId, assignmentNumber, description);
                assignmentsList.add(item);
                assignmentComboBox.addItem(item);
            }

            if (!assignmentsList.isEmpty()) {
                assignmentComboBox.setSelectedIndex(0);
                loadSubmissions(assignmentsList.get(0).id);
            } else {
                // No assignments
                JOptionPane.showMessageDialog(this, "No assignments found for this course.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading assignments", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSubmissions(int assignmentId) {
        String sql = "SELECT u.student_id, u.name, " +
                "       COALESCE(s.github_link, s.file_path) AS submission_link, " +
                "       s.submitted_at " +
                "FROM submissions s " +
                "JOIN users u ON s.student_id = u.id " +
                "WHERE s.assignment_id = ?";

        tableModel.setRowCount(0);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, assignmentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String studentName = rs.getString("name");
                String submissionLink = rs.getString("submission_link");
                String submittedAt = rs.getString("submitted_at");

                tableModel.addRow(new Object[]{studentId, studentName, submissionLink, submittedAt});
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading submissions", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class AssignmentItem {
        int id;
        int number;
        String description;

        AssignmentItem(int id, int number, String description) {
            this.id = id;
            this.number = number;
            this.description = description;
        }

        @Override
        public String toString() {
            return "Assignment " + number;
        }
    }
}
