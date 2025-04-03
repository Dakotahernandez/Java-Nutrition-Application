import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class LoginPage {

    public LoginPage() {
        JFrame frame = new JFrame();
        UserDatabase database = new UserDatabase();

        frame.setTitle("Login");
        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        JLabel username = new JLabel("Username:");
        username.setFont(new Font("Arial", Font.PLAIN, 14));
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.EAST;
        panel.add(username, c);

        JTextField usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usernameField, c);

        JLabel password = new JLabel("Password:");
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;
        panel.add(password, c);

        JTextField passwordField = new JTextField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(passwordField, c);

        JLabel message = new JLabel("");
        message.setFont(new Font("Arial", Font.PLAIN, 14));
        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(message, c);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        JButton login = new JButton("Login");
        login.setFont(new Font("Arial", Font.BOLD, 14));
        login.addActionListener(e -> {
            if (database.loginUser(usernameField.getText(), passwordField.getText())) {
                frame.dispose();
                new HomePage();
            }
            else {
                message.setForeground(Color.RED);
                message.setText("Invalid username or password. Try again.");
            }
        });

        JButton createAccount = new JButton("Create an Account");
        createAccount.setFont(new Font("Arial", Font.BOLD, 14));
        createAccount.addActionListener(e -> {
            if (database.registerUser(usernameField.getText(), passwordField.getText())) {
                message.setForeground(Color.BLACK);
                message.setText("New account created with username and password.");
                usernameField.setText("");
                passwordField.setText("");
            }
            else {
                message.setForeground(Color.RED);
                message.setText("Username or password cannot be empty. Try again.");
            }
        });

        buttonPanel.add(login);
        buttonPanel.add(createAccount);

        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        panel.add(buttonPanel, c);

        frame.add(panel);
        frame.setVisible(true);
    }

}
