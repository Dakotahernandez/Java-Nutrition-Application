import javax.swing.*;
import java.awt.*;

// This is the interface for creating an exercise
public class CreateExercise extends JFrame {

    public CreateExercise() {
        setTitle("Create Exercise");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        //Menu Bar panel
        JPanel menuBarPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        //Creating the menu
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
        //Combine the menu panel and progress bar panel
        contentPane.add(menuBarPanel, BorderLayout.NORTH);


        //Center panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;


        // exerciseName
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(new JLabel("Exercise Name:"), c);
        c.gridx = 1;
        JTextField exerciseName = new JTextField(15);
        formPanel.add(exerciseName, c);

        // Focus
        c.gridx = 0;
        c.gridy = 1;
        formPanel.add(new JLabel("Focus:"), c);
        c.gridx = 1;
        JTextField focus = new JTextField(15);
        formPanel.add(focus, c);

        // Description
        c.gridx = 0;
        c.gridy = 2;
        formPanel.add(new JLabel("Description:"), c);
        c.gridx = 1;
        JTextField description = new JTextField(15);
        formPanel.add(description, c);

        // Submit button
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("Add Entry");
        formPanel.add(submitButton, c);

        // Action listener for "Add Entry"
        submitButton.addActionListener(e -> {
            Exercise exercise = new Exercise(exerciseName.getText(), focus.getText(), description.getText());
            int i = exercise.writeCSV();

            String message = "";
            if(i==1) {
                message = "Exercise Created Successfully";
            }
            else if(i==0){
                message = "Exercise Already Exists";
            }
            else{
                message = "Exercise could not be added";
            }
            JOptionPane.showMessageDialog(this, message + "\n" +
                    "Exercise Name: " + exerciseName.getText() + "\n" +
                    "Focus: " + focus.getText() + "\n" + "Description: " + description.getText()

            );

            exerciseName.setText("");
            focus.setText("");
            description.setText("");
        });


        contentPane.add(formPanel, BorderLayout.CENTER);
        setVisible(true);
    }






    public static void main(String[] args) {
        new CreateExercise();
    }
}