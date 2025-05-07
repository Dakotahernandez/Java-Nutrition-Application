package tracking;

import frame.UserMenu;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class UserPlanDetails extends JPanel {
    private int userId;
    private Connection connection;
    private DefaultTableModel searchTableModel;
    private DefaultTableModel enrolledTableModel;
    private JTextField titleField;
    private JComboBox<String> levelCombo;
    private JComboBox<String> categoryCombo;
    private JTable searchTable;

    public UserPlanDetails(int userId) {
        this.userId = userId;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        initializeUI();
        loadEnrolledPlans();
    }

    public static void initializeTable() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:users.db");

        String sql = "CREATE TABLE IF NOT EXISTS UserPlans (" +
                     "user_id INTEGER NOT NULL, " +
                     "plan_id INTEGER NOT NULL, " +
                     "PRIMARY KEY (user_id, plan_id), " +
                     "FOREIGN KEY (user_id) REFERENCES users(id), " +
                     "FOREIGN KEY (plan_id) REFERENCES Plans(plan_id)" +
                     ")";
        try (Statement s = connection.createStatement()) {
            s.execute(sql);
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // SEARCH PANEL
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        // title
        c.gridx = 0;
        c.gridy = 0;
        searchPanel.add(new JLabel("Title:"), c);

        c.gridx = 1;
        c.gridy = 0;
        titleField = new JTextField(20);
        searchPanel.add(titleField, c);

        // level
        c.gridx = 0;
        c.gridy = 1;
        searchPanel.add(new JLabel("Level:"), c);

        c.gridx = 1;
        c.gridy = 1;
        levelCombo = new JComboBox<>(new String[]{"Any", "Beginner", "Intermediate", "Advanced"});
        searchPanel.add(levelCombo, c);

        // category
        c.gridx = 0;
        c.gridy = 2;
        searchPanel.add(new JLabel("Category:"), c);

        c.gridx = 1;
        c.gridy = 2;
        categoryCombo = new JComboBox<>(new String[]{"Any", "Strength", "Cardio", "HIIT", "Flexibility"});
        searchPanel.add(categoryCombo, c);

        // button
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        JButton searchButton = new JButton("Search");
        searchPanel.add(searchButton, c);

        add(searchPanel, BorderLayout.NORTH);

        // RESULTS
        JPanel searchTablePanel = new JPanel(new BorderLayout());
        searchTableModel = new DefaultTableModel(new String[]{"Title", "Duration (days)", "Level", "Category", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        searchTable = new JTable(searchTableModel);
        searchTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("Register"));
        searchTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), this::registerForPlan));
        searchTablePanel.add(new JScrollPane(searchTable), BorderLayout.CENTER);

        JPanel enrolledTablePanel = new JPanel(new BorderLayout());
        enrolledTableModel = new DefaultTableModel(new String[]{"Title", "Duration (days)", "Level", "Category", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        JTable enrolledTable = new JTable(enrolledTableModel);
        enrolledTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("Unregister"));
        enrolledTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), this::unregisterFromPlan));
        enrolledTablePanel.add(new JScrollPane(enrolledTable), BorderLayout.CENTER);

        // split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, searchTablePanel, enrolledTablePanel);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        // refresh
        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // event listener
        searchButton.addActionListener(e -> {
            searchPlans();
        });
        refreshButton.addActionListener(e -> {
            searchPlans();
            loadEnrolledPlans();
        });
    }

    private void searchPlans() {
        searchTableModel.setRowCount(0);
        StringBuilder sql = new StringBuilder(
                "SELECT p.plan_id, p.title, p.duration_days, p.level, p.category " +
                "FROM Plans p JOIN users u ON p.trainer_id = u.id " +
                "WHERE u.isTrainer = true");
        ArrayList<Object> params = new ArrayList<>();

        String title = titleField.getText().trim();
        if (!title.isEmpty()) {
            sql.append(" AND p.title LIKE ?");
            params.add("%" + title + "%");
        }

        String level = (String) levelCombo.getSelectedItem();
        if (!level.equals("Any")) {
            sql.append(" AND p.level = ?");
            params.add(level);
        }

        String category = (String) categoryCombo.getSelectedItem();
        if (!category.equals("Any")) {
            sql.append(" AND p.category = ?");
            params.add(category);
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int planId = rs.getInt("plan_id");
                searchTableModel.addRow(new Object[]{
                        rs.getString("title"),
                        rs.getInt("duration_days"),
                        rs.getString("level"),
                        rs.getString("category"),
                        planId
                });
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error searching plans: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadEnrolledPlans() {
        enrolledTableModel.setRowCount(0);
        String sql = "SELECT p.plan_id, p.title, p.duration_days, p.level, p.category " +
                     "FROM UserPlans up JOIN Plans p ON up.plan_id = p.plan_id " +
                     "WHERE up.user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int planId = rs.getInt("plan_id");
                enrolledTableModel.addRow(new Object[]{
                        rs.getString("title"),
                        rs.getInt("duration_days"),
                        rs.getString("level"),
                        rs.getString("category"),
                        planId
                });
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading enrolled plans: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerForPlan(int planId) {
        String sql = "INSERT INTO UserPlans (user_id, plan_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, planId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Sucessfully registered for plan!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            loadEnrolledPlans();
        }
        catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                JOptionPane.showMessageDialog(this,
                        "You already registered for this plan.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(this,
                        "Error registering for plan: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void unregisterFromPlan(int planId) {
        String sql = "DELETE FROM UserPlans WHERE user_id = ? AND plan_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, planId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this,
                        "Successfully unregistered from plan!",
                        "Success",
                        JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(this,
                        "You are not registered for this plan.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error unregistering from plan: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text) {
            setText(text);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            }
            else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int planId;
        private boolean isPushed;
        private final Consumer<Integer> action;

        public ButtonEditor(JCheckBox checkBox, Consumer<Integer> action) {
            super(checkBox);
            this.action = action;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            planId = (Integer) value;
            if (table.getColumnName(column).equals("Action")) {
                if (table == searchTable) {
                    button.setText("Register");
                }
                else {
                    button.setText("Unregister");
                }
            }
            else {
                button.setText("");
            }

            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            }
            else {
                button.setForeground(table.getForeground());
                button.setBackground(UIManager.getColor("Button.background"));
            }
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                action.accept(planId);
            }
            isPushed = false;
            return planId;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    public static void showUserPlanDetails(int userId) {
        JFrame frame = new JFrame("User Plans");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(UserMenu.addUserMenu(), BorderLayout.NORTH);
        frame.add(new UserPlanDetails(userId));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("User Classes");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(new UserPlanDetails(2));
//        frame.setSize(400, 300);
//        frame.setVisible(true);
//    }
}
