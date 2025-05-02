package tracking;

import user.TrainerExercise;
import user.TrainerWorkout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainerCreatePlan extends JPanel {
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JSpinner durationSpinner;
    private JComboBox<String> levelCombo;
    private JComboBox<String> categoryCombo;
    private JTable workoutTable;
    private DefaultTableModel workoutTableModel;
    private JTable exerciseTable;
    private DefaultTableModel exerciseTableModel;
    private List<TrainerWorkout> workouts;
    private List<List<TrainerExercise>> exercises; // List of exercises for each workout
    private int trainerId;
    private Connection connection;

    public TrainerCreatePlan(int trainerId) {
        this.trainerId = trainerId;
        this.workouts = new ArrayList<>();
        this.exercises = new ArrayList<>();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Plan Metadata
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        titleField = new JTextField(20);
        formPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionArea = new JTextArea(4, 20);
        formPanel.add(new JScrollPane(descriptionArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Duration (days):"), gbc);
        gbc.gridx = 1;
        durationSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1)); // Adjusted for days
        formPanel.add(durationSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Level:"), gbc);
        gbc.gridx = 1;
        levelCombo = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced"});
        formPanel.add(levelCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryCombo = new JComboBox<>(new String[]{"Strength", "Cardio", "HIIT", "Flexibility"});
        formPanel.add(categoryCombo, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Workouts Section
        JPanel workoutPanel = new JPanel(new BorderLayout());
        workoutTableModel = new DefaultTableModel(new String[]{"Day", "Name"}, 0); // Removed Week column
        workoutTable = new JTable(workoutTableModel);
        workoutTable.getSelectionModel().addListSelectionListener(e -> updateExerciseTable());
        workoutPanel.add(new JScrollPane(workoutTable), BorderLayout.CENTER);

        JPanel workoutButtons = new JPanel();
        JButton addWorkoutButton = new JButton("Add Workout");
        JButton editWorkoutButton = new JButton("Edit Workout");
        JButton deleteWorkoutButton = new JButton("Delete Workout");
        workoutButtons.add(addWorkoutButton);
        workoutButtons.add(editWorkoutButton);
        workoutButtons.add(deleteWorkoutButton);
        workoutPanel.add(workoutButtons, BorderLayout.SOUTH);

        // Exercises Section
        JPanel exercisePanel = new JPanel(new BorderLayout());
        exerciseTableModel = new DefaultTableModel(new String[]{"Name", "Sets", "Reps", "Rest (s)", "Instructions"}, 0);
        exerciseTable = new JTable(exerciseTableModel);
        exercisePanel.add(new JScrollPane(exerciseTable), BorderLayout.CENTER);

        JPanel exerciseButtons = new JPanel();
        JButton addExerciseButton = new JButton("Add Exercise");
        JButton editExerciseButton = new JButton("Edit Exercise");
        JButton deleteExerciseButton = new JButton("Delete Exercise");
        exerciseButtons.add(addExerciseButton);
        exerciseButtons.add(editExerciseButton);
        exerciseButtons.add(deleteExerciseButton);
        exercisePanel.add(exerciseButtons, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, workoutPanel, exercisePanel);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        // Action Buttons
        JPanel actionPanel = new JPanel();
        JButton saveButton = new JButton("Save Plan");
        JButton clearButton = new JButton("Clear");
        actionPanel.add(saveButton);
        actionPanel.add(clearButton);
        add(actionPanel, BorderLayout.SOUTH);

        // Event Listeners
        addWorkoutButton.addActionListener(e -> showWorkoutDialog(null, -1));
        editWorkoutButton.addActionListener(e -> {
            int selectedRow = workoutTable.getSelectedRow();
            if (selectedRow >= 0) {
                showWorkoutDialog(workouts.get(selectedRow), selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Select a workout to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        deleteWorkoutButton.addActionListener(e -> {
            int selectedRow = workoutTable.getSelectedRow();
            if (selectedRow >= 0) {
                workouts.remove(selectedRow);
                exercises.remove(selectedRow);
                workoutTableModel.removeRow(selectedRow);
                updateExerciseTable();
            } else {
                JOptionPane.showMessageDialog(this, "Select a workout to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addExerciseButton.addActionListener(e -> {
            int selectedWorkout = workoutTable.getSelectedRow();
            if (selectedWorkout >= 0) {
                showExerciseDialog(null, selectedWorkout, -1);
            } else {
                JOptionPane.showMessageDialog(this, "Select a workout to add an exercise.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        editExerciseButton.addActionListener(e -> {
            int selectedWorkout = workoutTable.getSelectedRow();
            int selectedExercise = exerciseTable.getSelectedRow();
            if (selectedWorkout >= 0 && selectedExercise >= 0) {
                showExerciseDialog(exercises.get(selectedWorkout).get(selectedExercise), selectedWorkout, selectedExercise);
            } else {
                JOptionPane.showMessageDialog(this, "Select an exercise to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        deleteExerciseButton.addActionListener(e -> {
            int selectedWorkout = workoutTable.getSelectedRow();
            int selectedExercise = exerciseTable.getSelectedRow();
            if (selectedWorkout >= 0 && selectedExercise >= 0) {
                exercises.get(selectedWorkout).remove(selectedExercise);
                exerciseTableModel.removeRow(selectedExercise);
            } else {
                JOptionPane.showMessageDialog(this, "Select an exercise to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        saveButton.addActionListener(e -> savePlan());
        clearButton.addActionListener(e -> clearForm());
    }

    private void showWorkoutDialog(TrainerWorkout workout, int index) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Workout Details", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Day:"), gbc);
        gbc.gridx = 1;
        JSpinner daySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1)); // Adjusted for days
        if (workout != null) daySpinner.setValue(workout.getDay());
        dialog.add(daySpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        if (workout != null) nameField.setText(workout.getName());
        dialog.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton saveButton = new JButton("Save");
        dialog.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Workout name is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            TrainerWorkout newWorkout = new TrainerWorkout(0, name, (Integer) daySpinner.getValue());
            if (index >= 0) {
                workouts.set(index, newWorkout);
                workoutTableModel.setValueAt(newWorkout.getDay(), index, 0);
                workoutTableModel.setValueAt(newWorkout.getName(), index, 1);
            }
            else {
                workouts.add(newWorkout);
                exercises.add(new ArrayList<>());
                workoutTableModel.addRow(new Object[]{newWorkout.getDay(), newWorkout.getName()});
            }
            dialog.dispose();
        });

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showExerciseDialog(TrainerExercise exercise, int workoutIndex, int exerciseIndex) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Exercise Details", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        if (exercise != null) nameField.setText(exercise.getName());
        dialog.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("Sets:"), gbc);
        gbc.gridx = 1;
        JSpinner setsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        if (exercise != null) setsSpinner.setValue(exercise.getSets());
        dialog.add(setsSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(new JLabel("Reps:"), gbc);
        gbc.gridx = 1;
        JSpinner repsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        if (exercise != null) repsSpinner.setValue(exercise.getReps());
        dialog.add(repsSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton saveButton = new JButton("Save");
        dialog.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Exercise name is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            TrainerExercise newExercise = new TrainerExercise(0, name, (Integer) setsSpinner.getValue(),
                    (Integer) repsSpinner.getValue());
            List<TrainerExercise> workoutExercises = exercises.get(workoutIndex);
            if (exerciseIndex >= 0) {
                workoutExercises.set(exerciseIndex, newExercise);
                exerciseTableModel.setValueAt(newExercise.getName(), exerciseIndex, 0);
                exerciseTableModel.setValueAt(newExercise.getSets(), exerciseIndex, 1);
                exerciseTableModel.setValueAt(newExercise.getReps(), exerciseIndex, 2);
            } else {
                workoutExercises.add(newExercise);
                exerciseTableModel.addRow(new Object[]{newExercise.getName(), newExercise.getSets(),
                        newExercise.getReps()});
            }
            dialog.dispose();
        });

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void updateExerciseTable() {
        int selectedRow = workoutTable.getSelectedRow();
        exerciseTableModel.setRowCount(0);
        if (selectedRow >= 0 && selectedRow < exercises.size()) {
            for (TrainerExercise exercise : exercises.get(selectedRow)) {
                exerciseTableModel.addRow(new Object[]{exercise.getName(), exercise.getSets(),
                        exercise.getReps()});
            }
        }
    }

    private void clearForm() {
        titleField.setText("");
        descriptionArea.setText("");
        durationSpinner.setValue(1);
        levelCombo.setSelectedIndex(0);
        categoryCombo.setSelectedIndex(0);
        workouts.clear();
        exercises.clear();
        workoutTableModel.setRowCount(0);
        exerciseTableModel.setRowCount(0);
    }

    private void savePlan() {
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();
        int durationDays = (Integer) durationSpinner.getValue();
        String level = (String) levelCombo.getSelectedItem();
        String category = (String) categoryCombo.getSelectedItem();

        if (title.isEmpty() || workouts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title and at least one workout are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            connection.setAutoCommit(false);

            // Save Plan
            String planSql = "INSERT INTO Plans (title, description, trainer_id, duration_days, level, category) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(planSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, title);
                pstmt.setString(2, description.isEmpty() ? null : description);
                pstmt.setInt(3, trainerId);
                pstmt.setInt(4, durationDays);
                pstmt.setString(5, level);
                pstmt.setString(6, category);
                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                int planId = rs.next() ? rs.getInt(1) : -1;

                // Save Workouts
                String workoutSql = "INSERT INTO Workouts (plan_id, name, day) VALUES (?, ?, ?)";
                try (PreparedStatement workoutPstmt = connection.prepareStatement(workoutSql, Statement.RETURN_GENERATED_KEYS)) {
                    for (int i = 0; i < workouts.size(); i++) {
                        TrainerWorkout workout = workouts.get(i);
                        workoutPstmt.setInt(1, planId);
                        workoutPstmt.setString(2, workout.getName());
                        workoutPstmt.setInt(3, workout.getDay());
                        workoutPstmt.executeUpdate();
                        ResultSet workoutRs = workoutPstmt.getGeneratedKeys();
                        int workoutId = workoutRs.next() ? workoutRs.getInt(1) : -1;

                        // Save Exercises
                        String exerciseSql = "INSERT INTO Exercises (workout_id, name, sets, reps) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement exercisePstmt = connection.prepareStatement(exerciseSql)) {
                            for (TrainerExercise exercise : exercises.get(i)) {
                                exercisePstmt.setInt(1, workoutId);
                                exercisePstmt.setString(2, exercise.getName());
                                exercisePstmt.setInt(3, exercise.getSets());
                                exercisePstmt.setInt(4, exercise.getReps());
                                exercisePstmt.executeUpdate();
                            }
                        }
                    }
                }
            }
            connection.commit();
            JOptionPane.showMessageDialog(this, "Plan saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();

        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving plan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            try {
                connection.setAutoCommit(true);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
