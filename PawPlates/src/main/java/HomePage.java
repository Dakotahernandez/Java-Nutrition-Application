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

    public JFrame frame;

    //HomePage Constructor:
    public HomePage() { setUp(); }

    // set up the main screen
    public void setUp() {
        frame = new JFrame("PawPlates");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // open the window to a full screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());
        frame.setContentPane(contentPane);

        // Create a panel to hold both menu bars
        JPanel menuBarPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add userMenuBar to the left
        UserMenu userMenu = new UserMenu();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        menuBarPanel.add(userMenu.addUserMenu(), gbc);

        // add logoutMenuBar to the right
        LogoutMenu logoutMenu = new LogoutMenu();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        menuBarPanel.add(logoutMenu.addLogoutMenu(), gbc);

        // Add the menu bar panel to the frame
        contentPane.add(menuBarPanel, BorderLayout.NORTH);

        // add other content to the frame's center if needed
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel helloUser = new JLabel("Hello, " + LoginPage.CURRENT_USER.getUsername());
        helloUser.setFont(new Font("Arial", Font.PLAIN, 30));
        c.gridx = 1;
        c.gridy = 0;
        mainPanel.add(helloUser, c);
        //fixing commit message
        // 🔧 [2025-04-23] Functional date button with hidden DatePicker (popup works)
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setAllowEmptyDates(false);
        dateSettings.setFormatForDatesCommonEra(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        DatePicker datePicker = new DatePicker(dateSettings);
        datePicker.setDate(LocalDate.now());

        // Allow popup functionality, hide visually
        datePicker.setOpaque(false);
        datePicker.setBackground(new Color(0, 0, 0, 0));
        datePicker.setBorder(null);
        datePicker.setPreferredSize(new Dimension(1, 1)); // tiny size but still visible to layout manager

        JButton calendarButton = new JButton(datePicker.getDate().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        calendarButton.addActionListener(e -> datePicker.openPopup());

        datePicker.addDateChangeListener(e -> {
            LocalDate selectedDate = e.getNewDate();
            if (selectedDate != null) {
                calendarButton.setText(selectedDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
            }
        });

        JPanel datePanel = new JPanel();
        datePanel.add(new JLabel("Today: "));
        datePanel.add(calendarButton);
        datePanel.add(datePicker);  // must be present in layout for popup to work

        c.gridx = 1;
        c.gridy = 1;
        mainPanel.add(datePanel, c);

        // ✅ Push image down to avoid overlap
        try {
            BufferedImage menuImage = ImageIO.read(new File("src/main/resources/PawPlates.png"));
            JLabel menuImageLabel = new JLabel(new ImageIcon(menuImage));
            c.gridx = 1;
            c.gridy = 2;
            mainPanel.add(menuImageLabel, c);
        } catch (IOException e) {
            e.printStackTrace();
        }

        c.gridy = 3;
        JButton logWorkoutButton = new JButton("Track Something...");
        c.gridx = 0;
        logWorkoutButton.addActionListener(e -> trackSomethingDialogue());
        mainPanel.add(logWorkoutButton, c);

        JButton logCalories = new JButton("Your Goals");
        c.gridx = 1;
        mainPanel.add(logCalories, c);

        JButton logSleep = new JButton("Your Reminders");
        c.gridx = 2;
        mainPanel.add(logSleep, c);

        contentPane.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void trackSomethingDialogue() {
        JDialog trackingDialogue = new JDialog(this, "Tracking Menu", true);
        trackingDialogue.setSize(250, 200);
        trackingDialogue.setLayout(new GridLayout(4, 1, 10, 10));

        JButton workout = new JButton("Workout");
        workout.addActionListener(e -> {
            new CreateExercise();
            frame.dispose();
            trackingDialogue.dispose();
        });

        JButton sleep = new JButton("Sleep");
        sleep.addActionListener(e -> {
            new SleepPage();
            frame.dispose();
            trackingDialogue.dispose();
        });

        JButton calories = new JButton("Calories");
        calories.addActionListener(e -> {
            new CalorieMacroPage();
            frame.dispose();
            trackingDialogue.dispose();
        });

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> trackingDialogue.dispose());

        trackingDialogue.add(workout);
        trackingDialogue.add(sleep);
        trackingDialogue.add(calories);
        trackingDialogue.add(cancel);

        trackingDialogue.setLocationRelativeTo(this);
        trackingDialogue.setVisible(true);
    }
}
