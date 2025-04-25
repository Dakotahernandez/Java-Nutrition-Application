/**
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
package org.example;

import javax.swing.*;
import java.awt.*;

public class ResetPasswordPage3 extends JFrame {
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public ResetPasswordPage3(String username) {
        setTitle("Set New Password");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel title = new JLabel("Set Your New Password", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        gbc.gridy = 0;
        add(title, gbc);

        JLabel label1 = new JLabel("Enter your new password:");
        label1.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 1;
        add(label1, gbc);

        JPasswordField pass1 = new JPasswordField(25);
        pass1.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 2;
        add(pass1, gbc);

        JLabel label2 = new JLabel("Confirm your new password:");
        label2.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 3;
        add(label2, gbc);

        JPasswordField pass2 = new JPasswordField(25);
        pass2.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 4;
        add(pass2, gbc);

        JButton submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.BOLD, 20));
        submit.setPreferredSize(new Dimension(200, 40));
        gbc.gridy = 5;
        add(submit, gbc);

        submit.addActionListener(e -> {
            String p1 = new String(pass1.getPassword()).trim();
            String p2 = new String(pass2.getPassword()).trim();
            if (p1.equals(p2)) {
                JOptionPane.showMessageDialog(this, "Success! Your password has been reset.");
                dispose(); // or redirect to login screen
            } else {
                JOptionPane.showMessageDialog(this, "Error: Passwords do not match.");
            }
        });

        setVisible(true);
    }
}
