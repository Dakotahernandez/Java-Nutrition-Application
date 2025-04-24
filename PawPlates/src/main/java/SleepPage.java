import javax.swing.*;
import java.awt.*;

public class SleepPage extends TemplateFrame{
    private static final int WEEKLY_GOAL = 60;
    private static int totalSleep = 0;

    public SleepPage() {
        setTitle("Sleep Tracker");
        JProgressBar sleepProgress = new JProgressBar(0, WEEKLY_GOAL);
        addMenuBarPanel();
        JLabel progressLabel = addProgressBar(sleepProgress, totalSleep, getProgressText());
        JTextField hoursSleptField = new JTextField(20);
        addTextField("Hours Slept Today", hoursSleptField,0,0);
        JButton enter = new JButton("Record Sleep");
        addButton(enter, 1);
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
        add(centerPanel, BorderLayout.CENTER);

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private static String getProgressText() {
        return String.format("Weekly Sleep Progress: %d / %d hours", totalSleep, WEEKLY_GOAL);
    }

    public static void main(String[] args){
        new SleepPage();
    }
}
