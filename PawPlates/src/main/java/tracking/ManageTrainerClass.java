package tracking;

import frame.LoginPage;
import frame.TemplateFrame;
import frame.Theme;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class ManageTrainerClass extends TemplateFrame {
    private static final WorkoutDatabase workoutDB = new WorkoutDatabase();
    private static final TrainerClassDatabase trainerClassDB = new TrainerClassDatabase();
    DefaultTableModel exerciseModel;

    public ManageTrainerClass() {
        int userId = LoginPage.CURRENT_USER.getId();
        setTitle("Manage Trainer Classes");
        addMenuBarPanel();

        DefaultTableCellRenderer leftHeaderRenderer = new DefaultTableCellRenderer();
        leftHeaderRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        TrainerClassDatabase trainerDB = new TrainerClassDatabase();
        List<TrainerClass> trainerClasses = trainerDB.loadTrainerClasses().stream()
                .filter(tc -> tc.getTrainerId() == userId)
                .toList();

        String[] classColNames = {"Date", "Name", "# Users", "Duration", "Calories", "# Exercises"};
        DefaultTableModel classModel = new DefaultTableModel(classColNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        trainerClasses.forEach(tc -> classModel.addRow(new Object[] {
                tc.getDate().toString(),
                tc.getName(),
                tc.getUserIds().size(),
                tc.getTotalDuration(),
                tc.getTotalCalories(),
                tc.getExerciseCount()
        }));

        JTable classTable = new JTable(classModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(classModel);
        classTable.setRowSorter(sorter);
        classTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        classTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        classTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        classTable.getColumnModel().getColumn(2).setPreferredWidth(75);
        classTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        classTable.getColumnModel().getColumn(4).setPreferredWidth(125);
        classTable.getColumnModel().getColumn(5).setPreferredWidth(100);

        JScrollPane classScrollPane = new JScrollPane(classTable);
        classScrollPane.setPreferredSize(new Dimension(500, 300));

        for (int i = 0; i < classTable.getColumnCount(); i++) {
            classTable.getColumnModel().getColumn(i).setHeaderRenderer(leftHeaderRenderer);
        }

        classScrollPane.setBorder(BorderFactory.createEmptyBorder());
        classTable.setShowGrid(true);
        classTable.setRowHeight(25);
        classTable.setFont(Theme.NORMAL_FONT);
        classTable.setBackground(Theme.BG_DARKER);
        classTable.setForeground(Theme.FG_LIGHT);
        classTable.setSelectionBackground(Theme.BG_LIGHTER);
        classTable.setSelectionForeground(Theme.FG_LIGHT);
        classTable.setGridColor(new Color(80, 80, 80));
        JTableHeader header = classTable.getTableHeader();
        header.setBackground(new Color(60, 60, 60));
        header.setForeground(Theme.FG_LIGHT);
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setReorderingAllowed(false);

        JPanel classPanel = new JPanel(new BorderLayout());
        classPanel.add(classScrollPane, BorderLayout.CENTER);
        TitledBorder border = BorderFactory.createTitledBorder("Your Trainer Classes");
        border.setTitleFont(new Font("SansSerif", Font.BOLD, 14));
        border.setTitleColor(Theme.FG_LIGHT);
        classPanel.setBorder(border);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 25);
        c.fill = GridBagConstraints.BOTH;
        centerPanel.add(classPanel, c);

        // Exercise table for selected class
        String[] exerciseColNames = {"Name", "Focus", "Reps", "Duration", "Calories", "Description"};
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
        exerciseTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        exerciseTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        exerciseTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        exerciseTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        exerciseTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        exerciseTable.getColumnModel().getColumn(5).setPreferredWidth(500);
        exerciseScrollPane.setPreferredSize(new Dimension(800, 300));

        for (int i = 0; i < exerciseTable.getColumnCount(); i++) {
            exerciseTable.getColumnModel().getColumn(i).setHeaderRenderer(leftHeaderRenderer);
        }

        TitledBorder exerciseBorder = BorderFactory.createTitledBorder("Highlight a class");
        exerciseBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 14));
        exerciseBorder.setTitleColor(Theme.FG_LIGHT);
        exercisePanel.setBorder(exerciseBorder);
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(10, 25, 10, 10);
        c.fill = GridBagConstraints.BOTH;
        centerPanel.add(exercisePanel, c);

        classTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = classTable.getSelectedRow();
                if (selectedRow >= 0 && selectedRow < trainerClasses.size()) {
                    TrainerClass selected = trainerClasses.get(selectedRow);
                    updateExerciseTable(selected.getExercises());
                    exerciseBorder.setTitle("Exercises for " + selected.getName());
                    exercisePanel.repaint();
                }
            }
        });

        setVisible(true);
    }

    private void updateExerciseTable(List<Exercise> exercises) {
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
