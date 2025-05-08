/**
 * =============================================================================
 * File: TrackTrainerClass.java
 * Author: Joshua Carroll
 * Created: 5/8/2025
 * -----------------------------------------------------------------------------
 * Description:
 * GUI frame that displays all available trainer-led classes to a user.
 * Users can register/unregister by checking the "Registered" checkbox,
 * which updates the database accordingly. Displays details like trainer name,
 * number of users, duration, and calories burned.
 *
 * Dependencies:
 * - javax.swing.*
 * - java.util.List
 * - tracking.TrainerClassDatabase
 * - tracking.TrainerClass
 * - tracking.Exercise
 * - frame.LoginPage
 * - frame.TemplateFrame
 * - frame.Theme
 * - user.User
 * - user.UserDatabase
 *
 * Usage:
 * new TrackTrainerClass(); // Launches the trainer class tracking GUI
 * =============================================================================
 */

package tracking;

import frame.LoginPage;
import frame.TemplateFrame;
import frame.Theme;
import user.UserDatabase;
import user.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TrackTrainerClass extends TemplateFrame {
        private static final TrainerClassDatabase trainerClassDB = new TrainerClassDatabase();

    public TrackTrainerClass() {
            int userId = LoginPage.CURRENT_USER.getId();
            setTitle("Track Classes");
            addMenuBarPanel();

            DefaultTableCellRenderer leftHeaderRenderer = new DefaultTableCellRenderer();
            leftHeaderRenderer.setHorizontalAlignment(SwingConstants.LEFT);

            List<TrainerClass> trainerClasses = trainerClassDB.loadTrainerClasses();
            List<TrainerClass> tableClasses = new ArrayList<>(); // to track rows
            String[] classColNames = {
                    "Registered", "Date", "Name", "Trainer", "# Users", "Duration", "Calories Burned", "# Exercises"
            };

            DefaultTableModel classModel = new DefaultTableModel(classColNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 0;
                }

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return columnIndex == 0 ? Boolean.class : String.class;
                }
            };

            trainerClasses.forEach(tc -> {
                String trainerName = "Unknown";
                try {
                    UserDatabase userDB = new UserDatabase();
                    User trainer = userDB.getUserById(tc.getTrainerId());
                    if (trainer != null) {
                        trainerName = trainer.getUsername();
                    }
                    userDB.close();
                } catch (Exception e) {
                    System.out.println("Error fetching trainer name for class ID " + tc.getId());
                }


                boolean isRegistered = tc.getUserIds().contains(userId);

                classModel.addRow(new Object[]{
                        isRegistered,
                        tc.getDate().toString(),
                        tc.getName(),
                        trainerName,
                        tc.getUserIds().size(),
                        tc.getTotalDuration(),
                        tc.getTotalCalories(),
                        tc.getExerciseCount()
                });

                tableClasses.add(tc);
            });

            JTable classTable = new JTable(classModel);
            TableRowSorter<DefaultTableModel> classSorter = new TableRowSorter<>(classModel);
            classTable.setRowSorter(classSorter);

            classTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            classTable.getColumnModel().getColumn(0).setPreferredWidth(90);  // Registered
            classTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Date
            classTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Name
            classTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Trainer
            classTable.getColumnModel().getColumn(4).setPreferredWidth(75);  // # Users
            classTable.getColumnModel().getColumn(5).setPreferredWidth(75);  // Duration
            classTable.getColumnModel().getColumn(6).setPreferredWidth(125); // Calories
            classTable.getColumnModel().getColumn(7).setPreferredWidth(100); // # Exercises

            for (int i = 0; i < classTable.getColumnCount(); i++) {
                classTable.getColumnModel().getColumn(i).setHeaderRenderer(leftHeaderRenderer);
            }

            JScrollPane classScrollPane = new JScrollPane(classTable);
            classScrollPane.setPreferredSize(new Dimension(800, 200));
            classScrollPane.setBorder(BorderFactory.createEmptyBorder());

            classTable.setShowGrid(true);
            classTable.setRowHeight(25);
            classTable.setFont(Theme.NORMAL_FONT);
            classTable.setBackground(Theme.BG_DARKER);
            classTable.setForeground(Theme.FG_LIGHT);
            classTable.setSelectionBackground(Theme.BG_LIGHTER);
            classTable.setSelectionForeground(Theme.FG_LIGHT);
            classTable.setGridColor(new Color(80, 80, 80));

            JTableHeader classHeader = classTable.getTableHeader();
            classHeader.setBackground(new Color(60, 60, 60));
            classHeader.setForeground(Theme.FG_LIGHT);
            classHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
            classHeader.setReorderingAllowed(false);

            JPanel classPanel = new JPanel(new BorderLayout());
            classPanel.add(classScrollPane, BorderLayout.CENTER);
            TitledBorder classBorder = BorderFactory.createTitledBorder("Available Classes");
            classBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 14));
            classBorder.setTitleColor(Theme.FG_LIGHT);
            classPanel.setBorder(classBorder);

            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(10, 10, 10, 25);
            c.fill = GridBagConstraints.BOTH;
            centerPanel.add(classPanel, c);

            classModel.addTableModelListener(e -> {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 0) {
                    int row = e.getFirstRow();
                    boolean checked = (boolean) classModel.getValueAt(row, 0);
                    TrainerClass tc = tableClasses.get(row);

                    if (checked) {
                        if (!tc.getUserIds().contains(userId)) {
                            tc.getUserIds().add(userId);
                        }
                    } else {
                        tc.getUserIds().remove(Integer.valueOf(userId));
                    }
                    trainerClassDB.updateUserList(tc);
                    classModel.setValueAt(tc.getUserIds().size(), row, 4);
                }
            });
            setVisible(true);
        }
}
