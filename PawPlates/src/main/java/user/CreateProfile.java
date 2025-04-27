package user; /**
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
//Sofia Amador
import javax.swing.*;
import java.awt.*;

/* NOTE: This file contains code (menu, logout) taken from other files for consistency throughout
the website general layout format, but this code is labelled as such for credit to the original programmer.
NEW CODE: Initial create profile interface. user.User provides first and last name, then has a dropdown
box for gender and fitness level. then they have 2 drop down boxes for security question
choices for resetting their password later if needed, and then they type in their answers.
* */
public class CreateProfile {
    //testing
    /*
    public static void main(String[] args) {
        setUp();
    }*/
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public static void setUp() {
        //code from other menu layout file
        JFrame frame = new JFrame("Create Profile");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel contentPane = new JPanel(new BorderLayout());
        frame.setContentPane(contentPane);
        JPanel menuBarPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        menuBarPanel.add(userMenuBar(), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        menuBarPanel.add(logoutMenuBar(), gbc);
        contentPane.add(menuBarPanel, BorderLayout.NORTH);

        //New Code Begins Here!
        contentPane.add(menuBarPanel, BorderLayout.NORTH);
        //Profile Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        // First Name
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(new JLabel("First Name:"), c);
        c.gridx = 1;
        JTextField firstNameField = new JTextField(15);
        formPanel.add(firstNameField, c);

        // Last Name
        c.gridx = 0;
        c.gridy = 1;
        formPanel.add(new JLabel("Last Name:"), c);
        c.gridx = 1;
        JTextField lastNameField = new JTextField(15);
        formPanel.add(lastNameField, c);

        // Gender Dropdown
        c.gridx = 0;
        c.gridy = 2;
        formPanel.add(new JLabel("Gender:"), c);
        c.gridx = 1;
        String[] genderOptions = {"Other", "Female", "Male"};
        JComboBox<String> genderDropdown = new JComboBox<>(genderOptions);
        formPanel.add(genderDropdown, c);

        // Fitness Level Dropdown
        c.gridx = 0;
        c.gridy = 3;
        formPanel.add(new JLabel("Fitness Level:"), c);
        c.gridx = 1;
        String[] fitnessLevels = {"Beginner", "Intermediate", "Advanced", "Elite"};
        JComboBox<String> fitnessDropdown = new JComboBox<>(fitnessLevels);
        formPanel.add(fitnessDropdown, c);

        // Security Question Section
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        formPanel.add(new JLabel("Security Questions for Password Reset:"), c);
        c.gridwidth = 1;

        // Security Question 1
        c.gridx = 0;
        c.gridy = 5;
        formPanel.add(new JLabel("Question 1:"), c);
        c.gridx = 1;
        String[] securityQuestions = {
                "What was the first programming language you learned?",
                "What is your favorite professor's name?",
                "What is the name of your favorite IDE?",
                "What was your favorite coding language?",
        };
        JComboBox<String> secQ1Dropdown = new JComboBox<>(securityQuestions);
        formPanel.add(secQ1Dropdown, c);

        // Answer 1
        c.gridx = 0;
        c.gridy = 6;
        formPanel.add(new JLabel("Answer 1:"), c);
        c.gridx = 1;
        JTextField answer1Field = new JTextField(15);
        formPanel.add(answer1Field, c);

        // Security Question 2
        c.gridx = 0;
        c.gridy = 7;
        formPanel.add(new JLabel("Question 2:"), c);
        c.gridx = 1;
        String[] securityQuestions2 = {
                "What is your mother's maiden name?",
                "What was the name of your first pet?",
                "What is the name of your birth state?",
                "What was your hardest CS class?",
        };
        JComboBox<String> secQ2Dropdown = new JComboBox<>(securityQuestions2);
        formPanel.add(secQ2Dropdown, c);

        // Answer 2
        c.gridx = 0;
        c.gridy = 8;
        formPanel.add(new JLabel("Answer 2:"), c);
        c.gridx = 1;
        JTextField answer2Field = new JTextField(15);
        formPanel.add(answer2Field, c);

        // Submit Button
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("Save Profile Changes");
        formPanel.add(submitButton, c);

        contentPane.add(formPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }


    //taken from other file for consistency
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public static JMenuBar userMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem trackWorkout = new JMenuItem("Track workout.Workout");
        JMenuItem trackCals = new JMenuItem("Track Calories");
        JMenuItem setGoal = new JMenuItem("Set a Goal");
        JMenuItem registration = new JMenuItem("Register for a Class");
        menu.add(trackWorkout);
        menu.add(trackCals);
        menu.add(setGoal);
        menu.add(registration);
        menuBar.add(menu);
        return menuBar;
    }
    //taken from other file for consistency
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public static JMenuBar logoutMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Logout");
        JMenuItem signOut = new JMenuItem("Sign Out");
        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem help = new JMenuItem("Help");
        menu.add(signOut);
        menu.add(settings);
        menu.add(help);
        menuBar.add(menu);
        return menuBar;
    }

    public static void main(String[] args) {
        setUp();
    }
}
