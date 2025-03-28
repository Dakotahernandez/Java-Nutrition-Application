import javax.swing.*;
import java.awt.*;

public class SleepPage {
    private static final int WEEKLY_GOAL = 60;
    private static int totalSleep = 0;
    private static JProgressBar SleepProgress;
    private static JLabel progressLabel;

    public SleepPage() {
        JFrame frame = new JFrame("Sleep Tracker");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        frame.setContentPane(contentPane);

        JPanel progressBarPanel = new JPanel();
        progressBarPanel.setLayout(new BoxLayout(progressBarPanel, BoxLayout.Y_AXIS));
        progressBarPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        SleepProgress = new JProgressBar(0, WEEKLY_GOAL);
        SleepProgress.setValue(totalSleep);
        SleepProgress.setStringPainted(true);
        SleepProgress.setPreferredSize(new Dimension(300, 60));

        progressLabel = new JLabel(getProgressText());
        progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        progressBarPanel.add(SleepProgress);
        progressBarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        progressBarPanel.add(progressLabel);

        contentPane.add(progressBarPanel);

        //----Top Section (Menu + Progress Bar)
        //Create a panel for the menu bars
        JPanel menuBarPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add menu bar to the left
        UserMenu userMenu = new UserMenu();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        menuBarPanel.add(userMenu.addUserMenu(), gbc);

        // Add logout menu bar to the right
        LogoutMenu logoutMenu = new LogoutMenu();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        menuBarPanel.add(logoutMenu.addLogoutMenu(), gbc);

        // Add the menu bar panel to the frame
        contentPane.add(menuBarPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(new JLabel("Hours Slept Today"), c);

        c.gridx = 1;
        JTextField hoursSleptField = new JTextField(20);
        hoursSleptField.setPreferredSize(new Dimension(250, 50));
        formPanel.add(hoursSleptField, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        JButton enter = new JButton("Record Sleep");
        formPanel.add(enter, c);

        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(formPanel);

        enter.addActionListener(e -> {
            String hoursSlept = hoursSleptField.getText();

            int hours;
            try {
                hours = Integer.parseInt(hoursSlept);
                if (hours < 0 || hours > 24) {
                    throw new IllegalArgumentException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter a valid number of sleep hours.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Sleep hours must be between 0 and 24.",
                        "Invalid Range",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            totalSleep += hours;

            SleepProgress.setValue(Math.min(totalSleep, WEEKLY_GOAL));
            progressLabel.setText(getProgressText());

            hoursSleptField.setText("");
        });

        frame.setVisible(true);
    }

    private static String getProgressText() {
        return String.format("Weekly Sleep Progress: %d / %d hours", totalSleep, WEEKLY_GOAL);
    }
}
