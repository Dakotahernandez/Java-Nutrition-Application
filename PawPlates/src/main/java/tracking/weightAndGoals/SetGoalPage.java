/**
 * =============================================================================
 * File:        SetGoalPage.java
 * Authors:     Faith Ota, Dakota Hernandez
 * Created:     04/24/2025
 * -----------------------------------------------------------------------------
 * Description:
 *   Page to display and manage user goals for weight, calories, and sleep.
 *   All goals are persisted in the database; other pages read these values.
 *
 * Dependencies:
 *   - frame.TemplateFrame
 *   - frame.LoginPage
 *   - frame.HomePage
 *   - frame.Theme
 *   - javax.swing.*
 *   - java.awt.*
 *   - java.sql.SQLException
 *
 * Usage:
 *   new SetGoalPage();
 *
 * =============================================================================
 */
package tracking.weightAndGoals;

import frame.TemplateFrame;
import frame.LoginPage;
import frame.HomePage;
import frame.Theme;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Page to display and manage user goals for weight, calories, and sleep.
 * All goals are persisted in the database; other pages read these values.
 */
public class SetGoalPage extends TemplateFrame {
    private final JTextField startingWeightField;
    private final JTextField goalWeightField;
    private final JTextField goalCalField;
    private final JTextField goalSleepField;
    private final WeightDatabase db;
    private final int userId;

    /**
     * Constructs the SetGoalPage for the current logged-in user.
     * Initializes and displays existing goals and provides UI for updating them.
     */
    public SetGoalPage() {
        // --- Menu Bar ---
        addMenuBarPanel();
        setTitle("Set A Goal");

        // --- Database ---
        db = new WeightDatabase();
        userId = LoginPage.CURRENT_USER.getId();

        Optional<WeightDatabase.WeightGoal> existing = Optional.empty();
        try {
            existing = db.getWeightGoal(userId);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading existing goals.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // --- Display current goals ---
        JPanel displayPanel = new JPanel(new GridLayout(0,1));
        displayPanel.setBackground(Theme.BG_DARK);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        displayPanel.add(createLabel("Current Goals"));

        double startW = 0, goalW = 0;
        int calG = 0, sleepG = 0;
        if (existing.isPresent()) {
            WeightDatabase.WeightGoal w = existing.get();
            startW = w.startWeight;
            goalW  = w.goalWeight;
            calG   = w.dailyCalGoal;
            sleepG = w.weeklySleepGoal;
            displayPanel.add(createLabel("Starting Weight: " + (int)startW + " lbs"));
            displayPanel.add(createLabel("Goal Weight:     " + (int)goalW  + " lbs"));
            displayPanel.add(createLabel("Daily Calories:  " + calG        + " kcal"));
            displayPanel.add(createLabel("Weekly Sleep:    " + sleepG      + " hrs"));
        } else {
            displayPanel.add(createLabel("No goals set yet."));
        }

        // --- Input fields ---
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Theme.BG_DARK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.anchor = GridBagConstraints.WEST;

        startingWeightField = new JTextField(String.valueOf((int)startW), 10);
        addTextField(inputPanel, "Starting Weight (lbs):", startingWeightField, gbc, 0);
        goalWeightField     = new JTextField(String.valueOf((int)goalW), 10);
        addTextField(inputPanel, "Goal Weight (lbs):",      goalWeightField,     gbc, 1);
        goalCalField        = new JTextField(String.valueOf(calG), 5);
        addTextField(inputPanel, "Daily Calorie Goal:",     goalCalField,        gbc, 2);
        goalSleepField      = new JTextField(String.valueOf(sleepG), 3);
        addTextField(inputPanel, "Weekly Sleep Goal (hrs):", goalSleepField,      gbc, 3);

        // --- Save button ---
        JButton save = new JButton("Save Goals");
        save.setBackground(Theme.ACCENT_COLOR);
        save.setForeground(Theme.FG_LIGHT);
        save.setFont(Theme.NORMAL_FONT);
        save.addActionListener(e -> onSave());
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Theme.BG_DARK);
        btnPanel.add(save);

        // --- Main content layout ---
        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        mainPanel.setBackground(Theme.BG_DARK);
        mainPanel.add(displayPanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Creates a styled JLabel with the given text using the application's theme.
     *
     * @param text the text to display in the label
     * @return a JLabel styled with the theme's font and color
     */
    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Theme.FG_LIGHT);
        l.setFont(Theme.NORMAL_FONT);
        return l;
    }
    /**
     * Adds a label and text field to the given panel using GridBagLayout constraints.
     *
     * @param p the parent panel to add components to
     * @param labelText the text for the associated label
     * @param field the JTextField to add next to the label
     * @param gbc the layout constraints for positioning
     * @param row the row number for vertical positioning
     */
    private void addTextField(JPanel p, String labelText, JTextField field,
                              GridBagConstraints gbc, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        JLabel lbl = new JLabel(labelText);
        lbl.setForeground(Theme.FG_LIGHT);
        lbl.setFont(Theme.NORMAL_FONT);
        p.add(lbl, gbc);
        gbc.gridx = 1;
        p.add(field, gbc);
    }


    /**
     * Handles the action of saving user-entered goal values to the database.
     * Validates and parses inputs, then saves via the WeightDatabase.
     * Displays success or error messages depending on the outcome.
     */
    private void onSave() {
        try {
            double s = Double.parseDouble(startingWeightField.getText());
            double g = Double.parseDouble(goalWeightField.getText());
            int c    = Integer.parseInt(goalCalField.getText());
            int w    = Integer.parseInt(goalSleepField.getText());
            db.setWeightGoal(userId, s, g, c, w);
            if(s < 1 || g < 1 || c < 1 || w < 1){
                throw new IllegalArgumentException();
            }
            JOptionPane.showMessageDialog(this, "Goals saved successfully.");
            dispose();
            new HomePage();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error saving goals.", "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(this, "Values must be positive", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
