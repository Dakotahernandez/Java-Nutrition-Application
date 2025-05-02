package tracking;

import frame.UserMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TrainerPlanDetails extends JPanel {
    private JFrame frame;
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
        add(buttonPanel, BorderLayout.SOUTH);

        createPlanButton.addActionListener(e -> showCreatePlanDialog());
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
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
