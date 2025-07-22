package main.view;

import main.model.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;

public class TakeAttendanceView extends JFrame {
    private int teacherId;
    private String courseCode;
    private LocalDate selectedDate;
    private HashMap<Integer, String> studentIdMap = new HashMap<>(); // Maps user.id to student_id
    private HashMap<Integer, JCheckBox> presentCheckMap = new HashMap<>();
    private HashMap<Integer, JCheckBox> absentCheckMap = new HashMap<>();

    public TakeAttendanceView(int teacherId, String courseCode) {
        this.teacherId = teacherId;
        this.courseCode = courseCode;
        this.selectedDate = LocalDate.now();

        setTitle("Take Attendance - " + courseCode);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel dateLabel = new JLabel("Date: " + selectedDate);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(dateLabel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new GridLayout(0, 6, 10, 10));
        tablePanel.add(new JLabel("Student ID"));
        tablePanel.add(new JLabel("Name"));
        tablePanel.add(new JLabel("Present (\u2713)"));
        tablePanel.add(new JLabel("Absent (\u2717)"));
        tablePanel.add(new JLabel("Present/Total")); // ✅ fixed label
        tablePanel.add(new JLabel("%"));

        String sql = "SELECT u.id, u.name, u.student_id FROM users u " +
                "JOIN student_courses sc ON u.id = sc.student_id " +
                "JOIN courses c ON sc.course_id = c.id " +
                "WHERE c.course_code = ? AND u.role = 'student'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, courseCode);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("id");
                String name = rs.getString("name");
                String studentId = rs.getString("student_id");
                studentIdMap.put(userId, studentId);

                JLabel idLabel = new JLabel(studentId);
                JLabel nameLabel = new JLabel(name);

                JCheckBox presentCheck = new JCheckBox();
                JCheckBox absentCheck = new JCheckBox();

                presentCheckMap.put(userId, presentCheck);
                absentCheckMap.put(userId, absentCheck);

                // Toggle logic
                presentCheck.addActionListener(e -> {
                    if (presentCheck.isSelected()) absentCheck.setSelected(false);
                });
                absentCheck.addActionListener(e -> {
                    if (absentCheck.isSelected()) presentCheck.setSelected(false);
                });

                // Attendance stats
                int totalDays = 0, presentDays = 0;
                String countSql = "SELECT COUNT(*) AS total, " +
                        "SUM(CASE WHEN status = 'present' THEN 1 ELSE 0 END) AS present " +
                        "FROM attendance WHERE student_id = ? AND course_id = (SELECT id FROM courses WHERE course_code = ?)";

                try (PreparedStatement countPs = conn.prepareStatement(countSql)) {
                    countPs.setInt(1, userId);
                    countPs.setString(2, courseCode);
                    ResultSet countRs = countPs.executeQuery();
                    if (countRs.next()) {
                        totalDays = countRs.getInt("total");       // ✅ removed +1
                        presentDays = countRs.getInt("present");
                    }
                }

                JLabel fractionLabel = new JLabel(presentDays + "/" + totalDays); // ✅ fixed order
                float percentage = totalDays > 0 ? (presentDays * 100f / totalDays) : 0;
                DecimalFormat df = new DecimalFormat("0.00");
                JLabel percentageLabel = new JLabel(df.format(percentage) + "%");

                tablePanel.add(idLabel);
                tablePanel.add(nameLabel);
                tablePanel.add(presentCheck);
                tablePanel.add(absentCheck);
                tablePanel.add(fractionLabel);
                tablePanel.add(percentageLabel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(tablePanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit Attendance");
        submitButton.addActionListener(e -> submitAttendance());
        mainPanel.add(submitButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void submitAttendance() {
        String courseIdQuery = "SELECT id FROM courses WHERE course_code = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement courseStmt = conn.prepareStatement(courseIdQuery)) {

            courseStmt.setString(1, courseCode);
            ResultSet rs = courseStmt.executeQuery();
            int courseId = -1;
            if (rs.next()) {
                courseId = rs.getInt("id");
            }

            String insertSql = "INSERT INTO attendance (student_id, course_id, date, status) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);

            for (int userId : studentIdMap.keySet()) {
                String status;
                if (presentCheckMap.get(userId).isSelected()) {
                    status = "present";
                } else if (absentCheckMap.get(userId).isSelected()) {
                    status = "absent";
                } else {
                    continue; // skip unmarked
                }

                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, courseId);
                insertStmt.setDate(3, Date.valueOf(selectedDate));
                insertStmt.setString(4, status);
                insertStmt.addBatch();
            }

            insertStmt.executeBatch();
            JOptionPane.showMessageDialog(this, "Attendance submitted successfully!");
            dispose();
            new DashboardTeacher(teacherId);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting attendance.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
