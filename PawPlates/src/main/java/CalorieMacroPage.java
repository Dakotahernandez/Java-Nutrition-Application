
import javax.swing.*;
import java.awt.*;
/**
 * NOTES
 * WHats done and explanation for why i did certain things
 * Have set value for calories since we dont have a user logged in with a value
 * Has 2 menus that pop up has options like logging out or help tabs--
 * ----None of the buttons do anything
 * I added a bar across the tops that shows the percent of total
 * ----calories consumed so far updates evrytime there is a new entry
 *There is a commented out snippet of code i was using for quick testing witjout
 * changing the main or worrying about messing with other files
 */
public class CalorieMacroPage extends JFrame{

    // Set daily calorie limit since there no db so far
    private static final int DAILY_LIMIT = 2000;

    // Track total calories consumed so far
    private static int totalCalsSoFar = 0;

    private static JProgressBar calorieProgressBar;
    private static JLabel progressLabel;


    /*
    //for testing the code
    public static void main(String[] args) {
        setUp();
    }
    */

    public CalorieMacroPage() { setUp(); }

    public static void setUp() {
        JFrame frame = new JFrame("Calorie/Macro Tracker");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Main content panel
        JPanel contentPane = new JPanel(new BorderLayout());
        frame.setContentPane(contentPane);

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

        // Create a panel for the progress bar
        JPanel progressBarPanel = new JPanel();
        progressBarPanel.setLayout(new BoxLayout(progressBarPanel, BoxLayout.Y_AXIS));
        progressBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize the progress bar
        calorieProgressBar = new JProgressBar(0, DAILY_LIMIT);
        calorieProgressBar.setValue(totalCalsSoFar);
        calorieProgressBar.setStringPainted(true); // shows % complete
        calorieProgressBar.setPreferredSize(new Dimension(400, 50));

        // Label below the progress bar
        progressLabel = new JLabel(getProgressText());
        progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add the progress bar and label to the panel
        progressBarPanel.add(calorieProgressBar);
        progressBarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        progressBarPanel.add(progressLabel);

        //Combine the menu panel and progress bar panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(menuBarPanel, BorderLayout.NORTH);
        topPanel.add(progressBarPanel, BorderLayout.CENTER);

        // Add the top panel to the frame
        contentPane.add(topPanel, BorderLayout.NORTH);

        //----Center Section (Form for Adding Entries)---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        // Food Name
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(new JLabel("Food Name:"), c);
        c.gridx = 1;
        JTextField foodNameField = new JTextField(15);
        formPanel.add(foodNameField, c);

        // Calories
        c.gridx = 0;
        c.gridy = 1;
        formPanel.add(new JLabel("Calories:"), c);
        c.gridx = 1;
        JTextField caloriesField = new JTextField(10);
        formPanel.add(caloriesField, c);

        // Carbs
        c.gridx = 0;
        c.gridy = 2;
        formPanel.add(new JLabel("Carbohydrates (g):"), c);
        c.gridx = 1;
        JTextField carbsField = new JTextField(10);
        formPanel.add(carbsField, c);

        // Protein
        c.gridx = 0;
        c.gridy = 3;
        formPanel.add(new JLabel("Protein (g):"), c);
        c.gridx = 1;
        JTextField proteinField = new JTextField(10);
        formPanel.add(proteinField, c);

        // Fat
        c.gridx = 0;
        c.gridy = 4;
        formPanel.add(new JLabel("Fat (g):"), c);
        c.gridx = 1;
        JTextField fatField = new JTextField(10);
        formPanel.add(fatField, c);

        // Submit button
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("Add Entry");
        formPanel.add(submitButton, c);

        // Action listener for "Add Entry"
        submitButton.addActionListener(e -> {
            String foodName = foodNameField.getText();
            String calStr = caloriesField.getText();
            String carbs = carbsField.getText();
            String protein = proteinField.getText();
            String fat = fatField.getText();

            // Parse and update total calories so far
            int cals = 0;
            try {
                cals = Integer.parseInt(calStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter a valid number for calories.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            totalCalsSoFar += cals;


            // Update the progress bar
            calorieProgressBar.setValue(Math.min(totalCalsSoFar, DAILY_LIMIT));
            progressLabel.setText(getProgressText());

            // Display the entered values in a dialog
            JOptionPane.showMessageDialog(frame,
                    "Entry Added:" +
                            "\nFood: " + foodName +
                            "\nCalories: " + calStr +
                            "\nCarbohydrates: " + carbs + " g" +
                            "\nProtein: " + protein + " g" +
                            "\nFat: " + fat + " g"
            );

            // Clear fields
            foodNameField.setText("");
            caloriesField.setText("");
            carbsField.setText("");
            proteinField.setText("");
            fatField.setText("");
        });

        // Add the form panel to the center of the content pane
        contentPane.add(formPanel, BorderLayout.CENTER);

        // Show the frame
        frame.setVisible(true);
    }

    // Helper method to display progress in text form
    private static String getProgressText() {
        if (totalCalsSoFar <= DAILY_LIMIT) {
            return String.format("Calories so far: %d / %d", totalCalsSoFar, DAILY_LIMIT);
        } else {
            int overBy = totalCalsSoFar - DAILY_LIMIT;
            return String.format("Calories so far: %d (Over by %d)", totalCalsSoFar, overBy);
        }
    }

}