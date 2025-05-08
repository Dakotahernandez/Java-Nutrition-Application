package frame;

import user.User;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Description
 *
 * @param
 * @return
 * @throws
 */
class CreateAccountPage extends JFrame {
    private final LoginPage loginPage;

    public CreateAccountPage(LoginPage loginPage) {
        this.loginPage = loginPage;

        setTitle("Create an Account");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        LoginPage.Background panel = new LoginPage.Background("src/main/resources/background.jpg");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        // title label
        JLabel title = new JLabel("Create a New Account", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setOpaque(true);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(title, c);

        // username label and field
        JLabel username = new JLabel("Username:");
        username.setFont(new Font("Arial", Font.PLAIN, 14));
        username.setOpaque(true);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.EAST;
        panel.add(username, c);

        JTextField usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        c.gridx = 1;
        c.gridy = 1;
        panel.add(usernameField, c);

        // email label and field
        JLabel email = new JLabel("Email:");
        email.setFont(new Font("Arial", Font.PLAIN, 14));
        email.setOpaque(true);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        panel.add(email, c);

        JTextField emailField = new JTextField(15);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        c.gridx = 1;
        c.gridy = 2;
        panel.add(emailField, c);

        // password label and field
        JLabel password = new JLabel("Password:");
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        password.setOpaque(true);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        panel.add(password, c);

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        c.gridx = 1;
        c.gridy = 3;
        panel.add(passwordField, c);

        // password label and field
        JLabel confirmPassword = new JLabel("Confirm Password:");
        confirmPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPassword.setOpaque(true);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        panel.add(confirmPassword, c);

        JPasswordField confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        c.gridx = 1;
        c.gridy = 4;
        panel.add(confirmPasswordField, c);

        // Account type field
        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        accountTypeLabel.setOpaque(true);
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        panel.add(accountTypeLabel, c);

        ButtonGroup trainerButtonGroup = new ButtonGroup();
        JRadioButton userButton = new JRadioButton("General User", true);
        trainerButtonGroup.add(userButton);

        JRadioButton trainerButton = new JRadioButton("Trainer");
        trainerButtonGroup.add(trainerButton);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.add(userButton);
        radioPanel.add(trainerButton);

        c.gridx = 1;
        c.gridy = 5;
        panel.add(radioPanel, c);

        // Message field
        JLabel message = new JLabel("");
        message.setFont(new Font("Arial", Font.PLAIN, 14));
        message.setOpaque(true);
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(message, c);

        // create account button
        JButton createAccount = new JButton("Create New Account");
        createAccount.setFont(new Font("Arial", Font.BOLD, 14));
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        createAccount.addActionListener(e -> {
            try {
                if (!Arrays.equals(passwordField.getPassword(), confirmPasswordField.getPassword())) {
                    throw new IllegalArgumentException("Passwords do not match. Try again.");
                }

                LoginPage.CURRENT_USER = new User(usernameField.getText(), passwordField.getText());
                LoginPage.CURRENT_USER.setEmail(emailField.getText());
                LoginPage.CURRENT_USER.setTrainer(trainerButton.isSelected());

                LoginPage.database.registerUser(LoginPage.CURRENT_USER);
                message.setForeground(Color.BLACK);
                message.setText("New account created!");
                usernameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                confirmPasswordField.setText("");

            } catch (IllegalArgumentException ex) {
                message.setForeground(Color.RED);
                message.setText(ex.getMessage());
            }
        });
        panel.add(createAccount, c);

        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.BOLD, 14));
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        back.addActionListener(e -> {
            new LoginPage();
            dispose();
        });

        panel.add(back, c);

        add(panel);

    }
}
