/**
 * =============================================================================
 * File:           HomePage.java
 * Authors:        Mac Johnson Dakota Hernandez Faith ota
 * Created:        04/20/25
 * -----------------------------------------------------------------------------
 * Description:
 *   Main application home screen frame with user greeting, themed date picker,
 *   logo display, and navigation buttons for tracking workouts, sleep, and calories and weight
 *
 * Dependencies:
 *   - com.github.lgooddatepicker.components.DatePicker
 *   - com.github.lgooddatepicker.components.DatePickerSettings
 *   - javax.imageio.ImageIO
 *   - javax.swing.*
 *   - java.awt.*
 *   - java.awt.image.BufferedImage
 *   - java.io.File
 *   - java.io.IOException
 *   - java.time.LocalDate
 *   - java.time.format.DateTimeFormatter
 *
 * Usage:
 *   // Instantiate and display the home page
 *   new HomePage();
 *
 * TODO:
 *
 * =============================================================================
 */

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
    private DatePicker datePicker;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MMMM d, yyyy");

    public HomePage() {
        setUp();
    }

    /**
     * Expose the currently selected date so other pages can use it.
     */
    public LocalDate getSelectedDate() {
        return datePicker.getDate();
    }

    private void setUp() {
        frame = new JFrame("PawPlates");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Content pane
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Theme.BG_DARK);
        frame.setContentPane(contentPane);

        // Top menu bar
        contentPane.add(UserMenu.addUserMenu(), BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Theme.BG_DARK);
        GridBagConstraints c = new GridBagConstraints();

        // Greeting
        JLabel helloUser = new JLabel("Hello, " + LoginPage.CURRENT_USER.getUsername());
        helloUser.setFont(Theme.HEADER_FONT);
        helloUser.setForeground(Theme.FG_LIGHT);
        c.gridx = 1;
        c.gridy = 0;
        mainPanel.add(helloUser, c);

        // DatePicker settings
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setAllowEmptyDates(false);
        dateSettings.setFormatForDatesCommonEra(FORMATTER);
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

        // Initialize DatePicker
        datePicker = new DatePicker(dateSettings);
        datePicker.setDate(LocalDate.now());
        SessionContext.setDate(datePicker.getDate());
        datePicker.setOpaque(false);
        datePicker.setBackground(new Color(0, 0, 0, 0));
        datePicker.setBorder(null);
        datePicker.setPreferredSize(new Dimension(1, 1));

        // Calendar button
        JButton calendarButton = new JButton(datePicker.getDate().format(FORMATTER));
        calendarButton.setFont(Theme.NORMAL_FONT);
        calendarButton.setBackground(Theme.BUTTON_BG);
        calendarButton.setForeground(Theme.BUTTON_FG);
        calendarButton.setBorder(BorderFactory.createLineBorder(Theme.MID_GRAY));
        calendarButton.addActionListener(e -> datePicker.openPopup());

        datePicker.addDateChangeListener(e -> {
            LocalDate newDate = e.getNewDate();
            if (newDate != null) {
                calendarButton.setText(newDate.format(FORMATTER));
                SessionContext.setDate(newDate);
            }
        });

        // Date panel
        JPanel datePanel = new JPanel();
        datePanel.setBackground(Theme.BG_DARK);
        JLabel dateLabel = new JLabel("Date: ");
        dateLabel.setForeground(Theme.FG_LIGHT);
        dateLabel.setFont(Theme.NORMAL_FONT);
        datePanel.add(dateLabel);
        datePanel.add(calendarButton);
        datePanel.add(datePicker);
        c.gridx = 1;
        c.gridy = 1;
        mainPanel.add(datePanel, c);

        // Logo image
        try {
            BufferedImage img = ImageIO.read(new File("src/main/resources/PawPlates.png"));
            JLabel imgLabel = new JLabel(new ImageIcon(img));
            c.gridx = 1;
            c.gridy = 2;
            mainPanel.add(imgLabel, c);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Bottom buttons
        c.gridy = 3;
        JButton track = new JButton("Track Something...");
        track.setFont(Theme.NORMAL_FONT);
        track.setBackground(Theme.BUTTON_BG);
        track.setForeground(Theme.BUTTON_FG);
        track.addActionListener(e -> trackSomethingDialogue());
        c.gridx = 0;
        mainPanel.add(track, c);

        JButton goals = new JButton("Your Goals");
        goals.setFont(Theme.NORMAL_FONT);
        goals.setBackground(Theme.BUTTON_BG);
        goals.setForeground(Theme.BUTTON_FG);
        c.gridx = 1;
        mainPanel.add(goals, c);

        JButton rem = new JButton("Your Reminders");
        rem.setFont(Theme.NORMAL_FONT);
        rem.setBackground(Theme.BUTTON_BG);
        rem.setForeground(Theme.BUTTON_FG);
        c.gridx = 2;
        mainPanel.add(rem, c);

        contentPane.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void trackSomethingDialogue() {
        JDialog dialog = new JDialog(this, "Tracking Menu", true);
        dialog.setSize(250, 250);
        dialog.setLayout(new GridLayout(5, 1, 10, 10));
        dialog.getContentPane().setBackground(Theme.BG_DARKER);

        String[] options = {"Workout", "Sleep", "Calories", "Weight", "Cancel"};
        for (String text : options) {
            JButton btn = new JButton(text);
            btn.setFont(Theme.NORMAL_FONT);
            btn.setBackground(Theme.BUTTON_BG);
            btn.setForeground(Theme.BUTTON_FG);
            btn.addActionListener(e -> {
                dialog.dispose();
                frame.dispose();
                switch (text) {
                    case "Workout":
                        new CreateExercise();
                        break;
                    case "Sleep":
                        new SleepPage(SessionContext.getDate());
                        break;
                    case "Calories":
                        new CalorieMacroPage(SessionContext.getDate());
                        break;
                    case "Weight":
                        new RecordWeight(SessionContext.getDate());
                        break;
                    default:
                        // Cancel
                        break;
                }
            });
            dialog.add(btn);
        }

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}

