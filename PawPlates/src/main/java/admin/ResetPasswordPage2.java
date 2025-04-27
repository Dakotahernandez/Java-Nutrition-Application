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
package admin;

import javax.swing.*;
import java.awt.*;

public class ResetPasswordPage2 extends JFrame {
    private JTextField answer1Field, answer2Field;
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public ResetPasswordPage2(String username, String[] qa) {
        setTitle("Security Questions");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel title = new JLabel("Answer Your Security Questions", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        gbc.gridy = 0;
        add(title, gbc);

        JLabel question1 = new JLabel(qa[0]);
        question1.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 1;
        add(question1, gbc);

        answer1Field = new JTextField(25);
        answer1Field.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 2;
        add(answer1Field, gbc);

        JLabel question2 = new JLabel(qa[2]);
        question2.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 3;
        add(question2, gbc);

        answer2Field = new JTextField(25);
        answer2Field.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 4;
        add(answer2Field, gbc);

        JButton submit = new JButton("Submit Answers");
        submit.setFont(new Font("Arial", Font.BOLD, 20));
        submit.setPreferredSize(new Dimension(200, 40));
        gbc.gridy = 5;
        add(submit, gbc);

        submit.addActionListener(e -> {
            String answer1 = answer1Field.getText().trim();
            String answer2 = answer2Field.getText().trim();
            if (answer1.equalsIgnoreCase(qa[1]) || answer2.equalsIgnoreCase(qa[3])) {
                dispose();
                new ResetPasswordPage3(username);
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect answers. Try again.");
            }
        });

        setVisible(true);
    }
}
