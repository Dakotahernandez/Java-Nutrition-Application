package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/*
this is a part of three files for reseting password. There are 3 pages: enter username, answer at least ones security question,
and making a new password. these files do not have menu title bar yet. I also have hard coded data to use the pages for now.
*/
public class ResetPasswordPage1 extends JFrame {
    private JTextField usernameField;
    private static HashMap<String, String[]> userDatabase;

    static {
        // username -> [question1, answer1, question2, answer2]
        userDatabase = new HashMap<>();
        userDatabase.put("user1", new String[]{"Question 1?", "test1", "Question 2?", "test2"});
        userDatabase.put("user2", new String[]{"Question 1?", "abc", "Question 2?", "abc"});
    }

    public ResetPasswordPage1() {
        setTitle("Reset Password");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel title = new JLabel("Reset Password", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        gbc.gridy = 0;
        add(title, gbc);

        JLabel instruction = new JLabel("Enter your username below to reset your password:", SwingConstants.CENTER);
        instruction.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 1;
        add(instruction, gbc);

        usernameField = new JTextField(25);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 2;
        add(usernameField, gbc);

        JButton submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.BOLD, 20));
        submit.setPreferredSize(new Dimension(150, 40));
        gbc.gridy = 3;
        add(submit, gbc);

        submit.addActionListener(e -> {
            String inputUsername = usernameField.getText().trim();
            if (userDatabase.containsKey(inputUsername)) {
                dispose();
                new ResetPasswordPage2(inputUsername, userDatabase.get(inputUsername));
            } else {
                JOptionPane.showMessageDialog(this, "Username not found. Please check for misspellings.");
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new ResetPasswordPage1();
    }
}
