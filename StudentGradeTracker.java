import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class StudentGradeTracker extends JFrame {
    private JTextField nameField;
    private JTextField gradeField;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JLabel averageLabel;
    private JLabel highestLabel;
    private JLabel lowestLabel;
    
    private ArrayList<Student> studentList;
    private final String CSV_FILE = "grades.csv";

    public StudentGradeTracker() {
        studentList = new ArrayList<>();
        initUI();
        loadGradesFromCSV();
        updateStatistics();
    }

    private void initUI() {
        setTitle("CodeAlpha - Student Grade Tracker");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Define styling colors
        Color primaryColor = new Color(32, 56, 100);  // Deep Blue
        Color bgColor = new Color(245, 245, 245);
        getContentPane().setBackground(bgColor);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryColor);
        JLabel titleLabel = new JLabel("STUDENT GRADE TRACKER");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Main Center Panel (Split into Left: Form, Right: Table)
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.setBackground(bgColor);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left Panel - Input Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Student Grade"));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        formPanel.add(nameLabel, gbc);

        nameField = new JTextField(12);
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(nameField, gbc);

        JLabel gradeLabel = new JLabel("Numeric Grade (0-100):");
        gradeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(gradeLabel, gbc);

        gradeField = new JTextField(12);
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(gradeField, gbc);

        JButton addButton = new JButton("Add Grade");
        addButton.setBackground(primaryColor);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(addButton, gbc);

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setBackground(new Color(150, 40, 40));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(deleteButton, gbc);

        centerPanel.add(formPanel);

        // Right Panel - Table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Gradebook Records"));
        tablePanel.setBackground(Color.WHITE);

        String[] columnNames = {"Student Name", "Grade"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(tablePanel);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel - Statistics Summary
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 10, 10),
                BorderFactory.createTitledBorder("Class Statistics Summary")
        ));
        statsPanel.setBackground(Color.WHITE);

        averageLabel = new JLabel("Average Grade: N/A", SwingConstants.CENTER);
        averageLabel.setFont(new Font("Arial", Font.BOLD, 12));
        averageLabel.setForeground(primaryColor);

        highestLabel = new JLabel("Highest Grade: N/A", SwingConstants.CENTER);
        highestLabel.setFont(new Font("Arial", Font.BOLD, 12));
        highestLabel.setForeground(new Color(40, 100, 40));

        lowestLabel = new JLabel("Lowest Grade: N/A", SwingConstants.CENTER);
        lowestLabel.setFont(new Font("Arial", Font.BOLD, 12));
        lowestLabel.setForeground(new Color(150, 60, 40));

        statsPanel.add(averageLabel);
        statsPanel.add(highestLabel);
        statsPanel.add(lowestLabel);
        add(statsPanel, BorderLayout.SOUTH);

        // Add Grade Action Listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        // Delete Grade Action Listener
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
    }

    private void addStudent() {
        String name = nameField.getText().trim();
        String gradeStr = gradeField.getText().trim();

        if (name.isEmpty() || gradeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both name and grade fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double grade = Double.parseDouble(gradeStr);
            if (grade < 0 || grade > 100) {
                JOptionPane.showMessageDialog(this, "Grade must be between 0 and 100.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Student student = new Student(name, grade);
            studentList.add(student);
            tableModel.addRow(new Object[]{student.getName(), student.getGrade()});
            saveGradesToCSV();
            updateStatistics();

            nameField.setText("");
            gradeField.setText("");
            nameField.requestFocus();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Grade must be a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student record from the table to delete.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        studentList.remove(selectedRow);
        tableModel.removeRow(selectedRow);
        saveGradesToCSV();
        updateStatistics();
    }

    private void updateStatistics() {
        if (studentList.isEmpty()) {
            averageLabel.setText("Average Grade: N/A");
            highestLabel.setText("Highest Grade: N/A");
            lowestLabel.setText("Lowest Grade: N/A");
            return;
        }

        double sum = 0;
        double highest = studentList.get(0).getGrade();
        double lowest = studentList.get(0).getGrade();
        String highestName = studentList.get(0).getName();
        String lowestName = studentList.get(0).getName();

        for (Student s : studentList) {
            double g = s.getGrade();
            sum += g;
            if (g > highest) {
                highest = g;
                highestName = s.getName();
            }
            if (g < lowest) {
                lowest = g;
                lowestName = s.getName();
            }
        }

        double average = sum / studentList.size();
        averageLabel.setText(String.format("Average Grade: %.2f", average));
        highestLabel.setText(String.format("Highest: %.1f (%s)", highest, highestName));
        lowestLabel.setText(String.format("Lowest: %.1f (%s)", lowest, lowestName));
    }

    private void saveGradesToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (Student s : studentList) {
                // Escape commas in student names
                String name = s.getName().replace(",", "\\,");
                writer.println(name + "," + s.getGrade());
            }
        } catch (IOException e) {
            System.err.println("Error saving grades: " + e.getMessage());
        }
    }

    private void loadGradesFromCSV() {
        File file = new File(CSV_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].replace("\\,", ",");
                    double grade = Double.parseDouble(parts[1]);
                    Student s = new Student(name, grade);
                    studentList.add(s);
                    tableModel.addRow(new Object[]{s.getName(), s.getGrade()});
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading grades: " + e.getMessage());
        }
    }

    // Custom Student Data Class
    private static class Student {
        private String name;
        private double grade;

        public Student(String name, double grade) {
            this.name = name;
            this.grade = grade;
        }

        public String getName() { return name; }
        public double getGrade() { return grade; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentGradeTracker().setVisible(true);
            }
        });
    }
}
