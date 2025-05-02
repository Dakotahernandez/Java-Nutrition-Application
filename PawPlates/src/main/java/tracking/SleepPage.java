package tracking; /**
 * =============================================================================
 * File:
 * Author:
 * Created:
 * -----------------------------------------------------------------------------
 * Description:
 *
 *
 * Dependencies:
 *
 *
 * Usage:
 *
 * =============================================================================
 */
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import frame.*;

public class SleepPage extends TemplateFrame {
    private static int WEEKLY_GOAL = 60;
    private static int totalSleep = 0;

    private final LocalDate date;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MMMM d, yyyy");

    /**
     * Constructor that accepts a date and builds the UI.
     */
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public SleepPage(LocalDate date) {
        this.date = date;
        addMenuBarPanel();
        setTitle("Sleep Tracker – " + date.format(FORMATTER));

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
     * No-arg constructor uses the date from frame.SessionContext.
     */
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public SleepPage() {
        this(SessionContext.getDate());
    }

    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    private String getProgressText() {
        return String.format("Weekly Sleep Progress: %d / %d hours",
                totalSleep, WEEKLY_GOAL);
    }

    public static void setWeeklyGoal(int weeklyGoal) {  WEEKLY_GOAL = weeklyGoal; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SleepPage::new);
    }
}
