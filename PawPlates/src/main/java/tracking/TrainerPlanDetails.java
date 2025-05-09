package tracking;

import frame.UserMenu;
import user.TrainerExercise;
import user.TrainerWorkout;
import user.TrainerWorkoutPlan;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class TrainerPlanDetails extends JPanel {
    private int trainerId;
    private Connection connection;
    private JTable planTable;
    private DefaultTableModel planTableModel;

    public TrainerPlanDetails(int trainerId) {
        this.trainerId = trainerId;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        initializeUI();
        loadPlans();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        planTableModel = new DefaultTableModel(new String[]{"Title", "Duration (days)", "Level", "Category"}, 0);
        planTable = new JTable(planTableModel);
        planTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(planTable);
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton createPlanButton = new JButton("Create New Plan");
        buttonPanel.add(createPlanButton);

        JButton editPlanButton = new JButton("Edit");
        editPlanButton.setEnabled(false);
        buttonPanel.add(editPlanButton);

        JButton deletePlanButton = new JButton("Delete");
        deletePlanButton.setEnabled(false);
        buttonPanel.add(deletePlanButton);

        JButton getInfoButton = new JButton("Get Info");
        getInfoButton.setEnabled(false);
        buttonPanel.add(getInfoButton);

        JButton refreshButton = new JButton("Refresh");
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

        planTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    deletePlanButton.setEnabled(planTable.getSelectedRow() >= 0);
                    editPlanButton.setEnabled(planTable.getSelectedRow() >= 0);
                    getInfoButton.setEnabled(planTable.getSelectedRow() >= 0);
                }
            }
        });

        createPlanButton.addActionListener(e -> showCreatePlanDialog());
        editPlanButton.addActionListener(e -> editPlan());
        deletePlanButton.addActionListener(e -> deletePlan());
        getInfoButton.addActionListener(e -> getPlanInfo());
        refreshButton.addActionListener(e -> loadPlans());
    }

    private void loadPlans() {
        planTableModel.setRowCount(0);
        String sql = "SELECT title, duration_days, level, category FROM Plans WHERE trainer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, trainerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                planTableModel.addRow(new Object[]{
                        rs.getString("title"),
                        rs.getInt("duration_days"),
                        rs.getString("level"),
                        rs.getString("category")
                });
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading plans: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editPlan() {
        int selectedRow = planTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a plan to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String title = (String) planTableModel.getValueAt(selectedRow, 0);
        String sql = "SELECT plan_id, title, description, level, category FROM Plans WHERE trainer_id = ? AND title = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);
            pstmt.setString(2, title);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int planId = rs.getInt("plan_id");
                    String currentTitle = rs.getString("title");
                    String currentDescription = rs.getString("description");
                    String currentLevel = rs.getString("level");
                    String currentCategory = rs.getString("category");

                    // Show edit dialog
                    showEditPlanDialog(planId, currentTitle, currentDescription, currentLevel, currentCategory);
                } else {
                    JOptionPane.showMessageDialog(this, "Plan not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading plan details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showEditPlanDialog(int planId, String currentTitle, String currentDescription, String currentLevel, String currentCategory) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Edit Plan", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        JTextField titleField = new JTextField(currentTitle, 20);
        dialog.add(titleField, gbc);

        // Description
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        JTextArea descriptionArea = new JTextArea(currentDescription != null ? currentDescription : "", 4, 20);
        dialog.add(new JScrollPane(descriptionArea), gbc);

        // Level
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(new JLabel("Level:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> levelCombo = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced"});
        levelCombo.setSelectedItem(currentLevel);
        dialog.add(levelCombo, gbc);

        // Category
        gbc.gridx = 0;
        gbc.gridy = 3;
        dialog.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> categoryCombo = new JComboBox<>(new String[]{"Strength", "Cardio", "HIIT", "Flexibility"});
        categoryCombo.setSelectedItem(currentCategory);
        dialog.add(categoryCombo, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, gbc);

        // Event Listeners
        saveButton.addActionListener(e -> {
            String newTitle = titleField.getText().trim();
            String newDescription = descriptionArea.getText().trim();
            String newLevel = (String) levelCombo.getSelectedItem();
            String newCategory = (String) categoryCombo.getSelectedItem();

            if (newTitle.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Title is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "UPDATE Plans SET title = ?, description = ?, level = ?, category = ? WHERE plan_id = ? AND trainer_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, newTitle);
                pstmt.setString(2, newDescription.isEmpty() ? null : newDescription);
                pstmt.setString(3, newLevel);
                pstmt.setString(4, newCategory);
                pstmt.setInt(5, planId);
                pstmt.setInt(6, trainerId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(dialog, "Plan updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    loadPlans(); // Refresh table
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to update plan. It may not exist or you lack permission.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Error updating plan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void deletePlan() {
        int selectedRow = planTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a plan to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String title = (String) planTableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete the plan '" + title + "'? This will also remove all associated workouts, exercises, and user registrations.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Retrieve plan_id for the selected plan
        String sql = "SELECT plan_id FROM Plans WHERE trainer_id = ? AND title = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);
            pstmt.setString(2, title);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int planId = rs.getInt("plan_id");

                // Delete the plan
                String deleteSql = "DELETE FROM Plans WHERE plan_id = ? AND trainer_id = ?";
                try (PreparedStatement deletePstmt = connection.prepareStatement(deleteSql)) {
                    deletePstmt.setInt(1, planId);
                    deletePstmt.setInt(2, trainerId);
                    int rowsAffected = deletePstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Plan deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadPlans(); // Refresh table
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete plan. It may not exist or you lack permission.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Plan not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting plan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getPlanInfo() {
        int selectedRow = planTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a plan to view registered users.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String title = (String) planTableModel.getValueAt(selectedRow, 0);
        String sql = "SELECT plan_id FROM Plans WHERE trainer_id = ? AND title = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, trainerId);
            ps.setString(2, title);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int planId = rs.getInt("plan_id");
                    showRegisteredUsersDialog(planId, title);
                }
                else {
                    JOptionPane.showMessageDialog(this,
                            "Plan not found.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error retrieving plan info: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showRegisteredUsersDialog(int planId, String planTitle) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Registered Users for " + planTitle, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());

        // Table for registered users
        DefaultTableModel userTableModel = new DefaultTableModel(new String[]{"User ID", "Username"}, 0);
        JTable userTable = new JTable(userTableModel);
        JScrollPane tableScrollPane = new JScrollPane(userTable);
        dialog.add(tableScrollPane, BorderLayout.CENTER);

        // Load registered users
        String sql = "SELECT u.id, u.username FROM UserPlans up JOIN Users u ON up.user_id = u.id WHERE up.plan_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, planId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    userTableModel.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("username")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(dialog, "Error loading registered users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Message if no users
        if (userTableModel.getRowCount() == 0) {
            dialog.add(new JLabel("No users are registered for this plan.", SwingConstants.CENTER), BorderLayout.NORTH);
        }

        // Close Button
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showCreatePlanDialog() {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Create New Plan", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());
        TrainerCreatePlan createPlanPanel = new TrainerCreatePlan(trainerId);
        dialog.add(createPlanPanel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Close");
        saveButton.addActionListener(e -> {
            dialog.dispose();
            loadPlans();
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(800, 800);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void showTrainerPlanDetails(int trainerId) {
        JFrame frame = new JFrame("Trainer Plans");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        TrainerPlanDetails contentPane = new TrainerPlanDetails(trainerId);
        contentPane.add(UserMenu.addUserMenu(), BorderLayout.NORTH);
        frame.add(contentPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
