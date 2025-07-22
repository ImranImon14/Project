package main.view;

import main.controller.AttendanceController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class StudentAttendanceView extends JFrame {
    private int studentId;
    private String courseCode;

    public StudentAttendanceView(int studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;

        setTitle("Attendance - " + courseCode);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Fetch attendance records (Date -> Boolean present/absent)
        Map<Date, Boolean> attendanceRecords = AttendanceController.getAttendanceRecords(studentId, courseCode);

        String[] columns = {"Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        int presentCount = 0;
        int totalCount = attendanceRecords.size();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<Date, Boolean> entry : attendanceRecords.entrySet()) {
            String dateStr = sdf.format(entry.getKey());
            String status = entry.getValue() ? "Present" : "Absent";
            if (entry.getValue()) presentCount++;
            model.addRow(new Object[]{dateStr, status});
        }

        double attendancePercent = totalCount == 0 ? 0 : (presentCount * 100.0 / totalCount);

        JLabel summaryLabel = new JLabel(String.format("Attendance: %d/%d (%.2f%% present)", presentCount, totalCount, attendancePercent));
        summaryLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            // Go back to student dashboard
            new DashboardStudent(studentId);
            dispose();
        });

        add(summaryLabel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
