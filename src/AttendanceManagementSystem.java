import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AttendanceManagementSystem implements ActionListener {
    private ArrayList<Student> students = new ArrayList<>();
    private String filename = "attendance.txt";
    private JFrame frame;
    private JTextField nameField;
    private JTextField rollNumberField;
    private JButton addButton;
    private JTextField rollNumberField2;
    private JButton markButton;
    private JButton saveButton;
    private JTable table;

    public AttendanceManagementSystem() {
        frame = new JFrame("Attendance Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);

        JPanel addPanel = new JPanel();
        addPanel.setPreferredSize(new Dimension(600, 50));
        addPanel.add(new JLabel("Name:"));
        nameField = new JTextField(20);
        addPanel.add(nameField);
        addPanel.add(new JLabel("Roll Number:"));
        rollNumberField = new JTextField(5);
        addPanel.add(rollNumberField);
        addButton = new JButton("Add");
        addButton.addActionListener(this);
        addPanel.add(addButton);

        JPanel markPanel = new JPanel();
        markPanel.setPreferredSize(new Dimension(600, 50));
        markPanel.add(new JLabel("Roll Number:"));
        rollNumberField2 = new JTextField(5);
        markPanel.add(rollNumberField2);
        markButton = new JButton("Mark Attendance");
        markButton.addActionListener(this);
        markPanel.add(markButton);

        JPanel savePanel = new JPanel();
        savePanel.setPreferredSize(new Dimension(600, 50));
        saveButton = new JButton("Save Attendance");
        saveButton.addActionListener(this);
        savePanel.add(saveButton);

        JPanel tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(600, 250));
        table = new JTable(new DefaultTableModel(new Object[]{"Name", "Roll Number", "Attendance"}, 0));
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);

        frame.add(addPanel, BorderLayout.NORTH);
        frame.add(markPanel, BorderLayout.CENTER);
        frame.add(savePanel, BorderLayout.SOUTH);
        frame.add(tablePanel, BorderLayout.SOUTH);

        loadAttendance();

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new AttendanceManagementSystem();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameField.getText();
            int rollNumber = Integer.parseInt(rollNumberField.getText());
            students.add(new Student(name, rollNumber));
            updateTable();
            nameField.setText("");
            rollNumberField.setText("");
            JOptionPane.showMessageDialog(frame, "Student added successfully.");
        } else if (e.getSource() == markButton) {
            int rollNumber = Integer.parseInt(rollNumberField2.getText());
            for (Student student : students) {
                if (student.getRollNumber() == rollNumber) {
                    student.markAttendance();
                    updateTable();
                    JOptionPane.showMessageDialog(frame, "Attendance marked for student: " + student.getName());
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Student with roll number " + rollNumber + " not found.");
        } else if (e.getSource() == saveButton) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                for (Student student : students) {
                    writer.write(student.getName() + "," + student.getRollNumber() + "," + student.getAttendance());
                    writer.newLine();
                }
                writer.close();
                JOptionPane.showMessageDialog(frame, "Attendance saved to file: " + filename);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error writing to file: " + filename);
            }
        }
    }

    private void loadAttendance() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int rollNumber = Integer.parseInt(parts[1]);
                int attendance = Integer.parseInt(parts[2]);
                students.add(new Student(name, rollNumber, attendance));
            }
            reader.close();
            updateTable();
            JOptionPane.showMessageDialog(frame, "Attendance loaded from file: " + filename);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading from file: " + filename);
        }
    }

    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Student student : students) {
            model.addRow(new Object[]{student.getName(), student.getRollNumber(), student.getAttendance()});
        }
    }
}