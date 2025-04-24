import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomePage extends JFrame {

    private JFrame frame;

    public HomePage() {
        setUp();
    }

    public void setUp() {
        frame = new JFrame("PawPlates");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Theme.BG_DARK);
        frame.setContentPane(contentPane);

        // Top menu bars
        JPanel menuBarPanel = new JPanel(new GridBagLayout());
        menuBarPanel.setBackground(Theme.BG_DARK);
        GridBagConstraints gbc = new GridBagConstraints();

        // User menu on left
        UserMenu userMenu = new UserMenu();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        menuBarPanel.add(userMenu.addUserMenu(), gbc);

        // Logout menu on right
        LogoutMenu logoutMenu = new LogoutMenu();
        gbc.gridx = 1; gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST;
        menuBarPanel.add(logoutMenu.addLogoutMenu(), gbc);

        contentPane.add(menuBarPanel, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Theme.BG_DARK);
        GridBagConstraints c = new GridBagConstraints();

        // Greeting
        JLabel helloUser = new JLabel("Hello, " + LoginPage.CURRENT_USER.getUsername());
        helloUser.setFont(Theme.HEADER_FONT);
        helloUser.setForeground(Theme.FG_LIGHT);
        c.gridx = 1; c.gridy = 0;
        mainPanel.add(helloUser, c);

        // DatePicker settings with dark theme
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setAllowEmptyDates(false);
        dateSettings.setFormatForDatesCommonEra(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundOverallCalendarPanel, Theme.BG_DARK);
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels, Theme.BG_DARK);
        dateSettings.setColor(DatePickerSettings.DateArea.TextMonthAndYearMenuLabels, Theme.FG_LIGHT);
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearNavigationButtons, Theme.BG_DARK);
        dateSettings.setColor(DatePickerSettings.DateArea.TextMonthAndYearNavigationButtons, Theme.FG_LIGHT);
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundTopLeftLabelAboveWeekNumbers, Theme.BG_DARK);
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundCalendarPanelLabelsOnHover, Theme.MID_GRAY);
        dateSettings.setColor(DatePickerSettings.DateArea.CalendarTextWeekdays, Theme.FG_LIGHT);
        dateSettings.setColor(DatePickerSettings.DateArea.CalendarTextWeekNumbers, Theme.FG_LIGHT);
        dateSettings.setColor(DatePickerSettings.DateArea.CalendarTextNormalDates, Theme.FG_LIGHT);
        dateSettings.setColor(DatePickerSettings.DateArea.CalendarBackgroundNormalDates, Theme.BG_DARKER);
        dateSettings.setColor(DatePickerSettings.DateArea.CalendarBackgroundVetoedDates, Theme.BG_DARK);
        dateSettings.setColor(DatePickerSettings.DateArea.CalendarBackgroundSelectedDate, Theme.ACCENT_GREEN);
        dateSettings.setColor(DatePickerSettings.DateArea.DatePickerTextValidDate, Theme.FG_LIGHT);
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundTodayLabel, Theme.BG_DARK);
        dateSettings.setColor(DatePickerSettings.DateArea.TextTodayLabel, Theme.FG_LIGHT);
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundClearLabel, Theme.BG_DARK);
        dateSettings.setColor(DatePickerSettings.DateArea.TextClearLabel, Theme.FG_LIGHT);

        // DatePicker component
        DatePicker datePicker = new DatePicker(dateSettings);
        datePicker.setDate(LocalDate.now());
        datePicker.setOpaque(false);
        datePicker.setBackground(new Color(0,0,0,0));
        datePicker.setBorder(null);
        datePicker.setPreferredSize(new Dimension(1,1));

        // Calendar button
        JButton calendarButton = new JButton(datePicker.getDate().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        calendarButton.setFont(Theme.NORMAL_FONT);
        calendarButton.setBackground(Theme.BUTTON_BG);
        calendarButton.setForeground(Theme.BUTTON_FG);
        calendarButton.setBorder(BorderFactory.createLineBorder(Theme.MID_GRAY));
        calendarButton.addActionListener(e -> datePicker.openPopup());

        datePicker.addDateChangeListener(e -> {
            LocalDate newDate = e.getNewDate();
            if (newDate != null) {
                calendarButton.setText(newDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
            }
        });

        JPanel datePanel = new JPanel();
        datePanel.setBackground(Theme.BG_DARK);
        JLabel dateLabel = new JLabel("Date: ");
        dateLabel.setForeground(Theme.FG_LIGHT);
        dateLabel.setFont(Theme.NORMAL_FONT);
        datePanel.add(dateLabel);
        datePanel.add(calendarButton);
        datePanel.add(datePicker);

        c.gridx = 1; c.gridy = 1;
        mainPanel.add(datePanel, c);

        // Logo image
        try {
            BufferedImage img = ImageIO.read(new File("src/main/resources/PawPlates.png"));
            JLabel imgLabel = new JLabel(new ImageIcon(img));
            c.gridx = 1; c.gridy = 2;
            mainPanel.add(imgLabel, c);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Bottom buttons
        c.gridy = 3;
        JButton track = new JButton("Track Something...");
        track.setBackground(Theme.BUTTON_BG); track.setForeground(Theme.BUTTON_FG);
        track.setFont(Theme.NORMAL_FONT);
        c.gridx = 0;
        track.addActionListener(e -> trackSomethingDialogue());
        mainPanel.add(track, c);

        JButton goals = new JButton("Your Goals");
        goals.setBackground(Theme.BUTTON_BG); goals.setForeground(Theme.BUTTON_FG);
        goals.setFont(Theme.NORMAL_FONT);
        c.gridx = 1;
        mainPanel.add(goals, c);

        JButton rem = new JButton("Your Reminders");
        rem.setBackground(Theme.BUTTON_BG); rem.setForeground(Theme.BUTTON_FG);
        rem.setFont(Theme.NORMAL_FONT);
        c.gridx = 2;
        mainPanel.add(rem, c);

        contentPane.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void trackSomethingDialogue() {
        JDialog dialog = new JDialog(this, "Tracking Menu", true);
        dialog.setSize(250, 200);
        dialog.setLayout(new GridLayout(4, 1, 10, 10));
        dialog.getContentPane().setBackground(Theme.BG_DARKER);
        String[] labels = {"Workout","Sleep","Calories","Cancel"};
        for (String text : labels) {
            JButton btn = new JButton(text);
            btn.setBackground(Theme.BUTTON_BG); btn.setForeground(Theme.BUTTON_FG);
            btn.setFont(Theme.NORMAL_FONT);
            btn.addActionListener(e -> {
                if (text.equals("Workout")) new CreateExercise();
                else if (text.equals("Sleep")) new SleepPage();
                else if (text.equals("Calories")) new CalorieMacroPage();
                dialog.dispose(); frame.dispose();
            });
            dialog.add(btn);
        }
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
