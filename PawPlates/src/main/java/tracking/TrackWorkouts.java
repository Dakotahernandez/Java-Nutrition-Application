package tracking;
import frame.LoginPage;
import frame.TemplateFrame;
import frame.Theme;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.util.List;

public class TrackWorkouts extends TemplateFrame{
    private static final WorkoutDatabase workoutDB = new WorkoutDatabase();
    DefaultTableModel exerciseModel;


    public TrackWorkouts() {
        int userId = LoginPage.CURRENT_USER.getId();
        setTitle("Track Workouts");
        addMenuBarPanel();


        // workout table
        DefaultTableCellRenderer leftHeaderRenderer = new DefaultTableCellRenderer();
        leftHeaderRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        List<Workout> workouts = workoutDB.loadWorkoutsForUser(userId);
        String[] workoutColNames = {"Date", "Name", "Duration", "Calories Burned", "# Exercises"};
        DefaultTableModel workoutModel = new DefaultTableModel(workoutColNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        workouts.forEach(w -> workoutModel.addRow(new Object[] {
                w.getDate().toString(),
                w.getName(),
                w.getTotalDuration(),
                w.getTotalCalories(),
                w.getExerciseCount()
        }));
        JTable workoutTable = new JTable(workoutModel);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(workoutModel);
        workoutTable.setRowSorter(sorter);

        workoutTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        workoutTable.getColumnModel().getColumn(0).setPreferredWidth(100);// date
        workoutTable.getColumnModel().getColumn(1).setPreferredWidth(200);// name
        workoutTable.getColumnModel().getColumn(2).setPreferredWidth(75); // Duration
        workoutTable.getColumnModel().getColumn(3).setPreferredWidth(125);// Calories
        workoutTable.getColumnModel().getColumn(4).setPreferredWidth(100);// Count
        JScrollPane workoutScrollPane = new JScrollPane(workoutTable);
        workoutScrollPane.setPreferredSize(new Dimension(600, 300));

        for (int i = 0; i < workoutTable.getColumnCount(); i++) {
            workoutTable.getColumnModel().getColumn(i).setHeaderRenderer(leftHeaderRenderer);
        }

        workoutScrollPane.setBorder(BorderFactory.createEmptyBorder());
        workoutTable.setShowGrid(true);
        workoutTable.setRowHeight(25);
        workoutTable.setFont(Theme.NORMAL_FONT);
        workoutTable.setBackground(Theme.BG_DARKER);
        workoutTable.setForeground(Theme.FG_LIGHT);
        workoutTable.setSelectionBackground(Theme.BG_LIGHTER);
        workoutTable.setSelectionForeground(Theme.FG_LIGHT);
        workoutTable.setGridColor(new Color(80, 80, 80));
        JTableHeader header = workoutTable.getTableHeader();
        header.setBackground(new Color(60, 60, 60));
        header.setForeground(Theme.FG_LIGHT);
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setReorderingAllowed(false);

        JPanel workoutPanel = new JPanel(new BorderLayout());
        workoutPanel.add(workoutScrollPane, BorderLayout.CENTER);
        TitledBorder border = BorderFactory.createTitledBorder("Your Workouts");
        border.setTitleFont(new Font("SansSerif", Font.BOLD, 14));
        border.setTitleColor(Theme.FG_LIGHT);
        workoutPanel.setBorder(border);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 25);
        c.fill = GridBagConstraints.BOTH;
        centerPanel.add(workoutPanel, c);


        //Exercise table
        String[] exerciseColNames = { "Name", "Focus", "Reps", "Duration", "Calories", "Description"};
        exerciseModel = new DefaultTableModel(exerciseColNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable exerciseTable = new JTable(exerciseModel);
        exerciseTable.setShowGrid(true);
        exerciseTable.setRowHeight(25);
        exerciseTable.setFont(Theme.NORMAL_FONT);
        exerciseTable.setBackground(Theme.BG_DARKER);
        exerciseTable.setForeground(Theme.FG_LIGHT);
        exerciseTable.setSelectionBackground(Theme.BG_LIGHTER);
        exerciseTable.setSelectionForeground(Theme.FG_LIGHT);
        exerciseTable.setGridColor(new Color(80, 80, 80));
        JTableHeader exerciseHeader = exerciseTable.getTableHeader();
        exerciseHeader.setBackground(new Color(60, 60, 60));
        exerciseHeader.setForeground(Theme.FG_LIGHT);
        exerciseHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        exerciseHeader.setReorderingAllowed(false);

        JScrollPane exerciseScrollPane = new JScrollPane(exerciseTable);
        exerciseScrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel exercisePanel = new JPanel(new BorderLayout());
        exercisePanel.add(exerciseScrollPane, BorderLayout.CENTER);

        TableRowSorter<DefaultTableModel> sorter1 = new TableRowSorter<>(exerciseModel);
        exerciseTable.setRowSorter(sorter1);

        exerciseTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        exerciseTable.getColumnModel().getColumn(0).setPreferredWidth(200); // Name
        exerciseTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Focus
        exerciseTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Reps
        exerciseTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Duration
        exerciseTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Calories
        exerciseTable.getColumnModel().getColumn(5).setPreferredWidth(500); // Description
        exerciseScrollPane.setPreferredSize(new Dimension(1000, 300));

        for (int i = 0; i < exerciseTable.getColumnCount(); i++) {
            exerciseTable.getColumnModel().getColumn(i).setHeaderRenderer(leftHeaderRenderer);
        }

        TitledBorder exerciseBorder = BorderFactory.createTitledBorder("Highlight an exercise to view");
        exerciseBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 14));
        exerciseBorder.setTitleColor(Theme.FG_LIGHT);
        exercisePanel.setBorder(exerciseBorder);
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(10, 25, 10, 10);
        c.fill = GridBagConstraints.BOTH;
        centerPanel.add(exercisePanel, c);





        workoutTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = workoutTable.getSelectedRow();
                if (selectedRow >= 0 && selectedRow < workouts.size()) {
                    Workout selectedWorkout = workouts.get(selectedRow);
                    int workoutId = selectedWorkout.getId();

                    updateExerciseTable(workoutId);
                    exerciseBorder.setTitle("Exercises for " + selectedWorkout.getName());
                    exercisePanel.repaint();
                }
            }
        });


        setVisible(true);
    }

    private void updateExerciseTable(int workoutId) {
        List<Exercise> exercises = workoutDB.loadExercisesForWorkout(workoutId);
        exerciseModel.setRowCount(0);
        exercises.stream()
                .map(e -> new Object[] {
                        e.getName(),
                        e.getFocus(),
                        e.getReps(),
                        e.getDuration(),
                        e.getCaloriesBurned(),
                        e.getDescription()
                })
                .forEach(exerciseModel::addRow);
    }

}
