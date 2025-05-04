package tracking.weightAndGoals;

/**
 * =============================================================================
 * File:        SleepPage.java
 * Author:      Dakota Hernandez, Faith ota, Joshua Carroll
 * Created:     04/25/2025
 * -----------------------------------------------------------------------------
 * Description:
 *   A Java Swing page for tracking daily sleep hours and comparing progress
 *   against a weekly sleep goal. Displays a progress bar and input form.
 *   Reads the user's goal from the database and updates sleep total in memory.
 *
 * Dependencies:
 *   - javax.swing.*
 *   - java.awt.*
 *   - java.time.LocalDate
 *   - java.time.format.DateTimeFormatter
 *   - frame.TemplateFrame
 *   - frame.LoginPage
 *   - tracking.weightAndGoals.WeightDatabase
 *
 * Usage:
 *   new SleepPage();                       // Uses current date from SessionContext
 *   new SleepPage(LocalDate.of(2025, 5, 4)); // Uses a specific date
 *
 * =============================================================================
 */

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import frame.*;

import java.sql.SQLException;

public class SleepPage extends TemplateFrame {
    private static int WEEKLY_GOAL;
    private static int totalSleep = 0;

    private final LocalDate date;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MMMM d, yyyy");

    /**
     * Constructs the SleepPage for a given date, initializing UI and goal data.
     *
     * @param date the date to display and use in the header
     */
    public SleepPage(LocalDate date) {
        this.date = date;
        addMenuBarPanel();
        setTitle("Sleep Tracker – " + date.format(FORMATTER));

        try {
            WeightDatabase db = new WeightDatabase();
            int userId = LoginPage.CURRENT_USER.getId();
            WEEKLY_GOAL = db.getWeightGoal(userId)
                    .map(g -> g.weeklySleepGoal)
                    .orElse(60);
        } catch (SQLException ex) {
            ex.printStackTrace();
            WEEKLY_GOAL = 60;
        }

        JProgressBar sleepProgress = new JProgressBar(0, WEEKLY_GOAL);
        JLabel progressLabel =
                addProgressBar(sleepProgress, totalSleep, getProgressText());

        JTextField hoursSleptField = new JTextField(20);
        addTextField("Hours Slept Today", hoursSleptField, 0, 0);

        JButton enter = new JButton("Record Sleep");
        enter.addActionListener(e -> {
            String hoursSlept = hoursSleptField.getText();
            int hours;
            try {
                hours = Integer.parseInt(hoursSlept);
                if (hours < 0 || hours > 24) {
                    throw new IllegalArgumentException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number of sleep hours.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,
                        "Sleep hours must be between 0 and 24.",
                        "Invalid Range",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            totalSleep += hours;
            sleepProgress.setValue(Math.min(totalSleep, WEEKLY_GOAL));
            progressLabel.setText(getProgressText());
            hoursSleptField.setText("");
        });
        addButton(enter, 1);

        // Add the center panel (inherited from frame.TemplateFrame)
        add(centerPanel, BorderLayout.CENTER);

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    /**
     * Constructs the SleepPage using the current date from SessionContext.
     */
    public SleepPage() {
        this(SessionContext.getDate());
    }

    /**
     * Returns a formatted progress string indicating weekly sleep status.
     *
     * @return the formatted string showing total sleep out of goal
     */
    private String getProgressText() {
        return String.format("Weekly Sleep Progress: %d / %d hours",
                totalSleep, WEEKLY_GOAL);
    }

    /**
     * Updates the weekly sleep goal value used in the progress bar.
     *
     * @param weeklyGoal the new sleep goal in hours
     */
    public static void setWeeklyGoal(int weeklyGoal) {
        WEEKLY_GOAL = weeklyGoal;
    }

    /**
     * Main method for launching the SleepPage UI independently.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SleepPage::new);
    }
}
