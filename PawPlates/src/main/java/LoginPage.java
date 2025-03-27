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

        frame.setTitle("Login");
        frame.setSize(400, 300);
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
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                try {
                    Scanner scanner = new Scanner(new File("src/main/java/database.csv"));
                    scanner.nextLine();
                    while (scanner.hasNextLine()) {
                        String[] data = scanner.nextLine().split(",");
                        if (username.equals(data[0]) && password.equals(data[1])) {
                            message.setText("Login Successful");
                            frame.dispose();
                            new HomePage();
                            break;
                        }
                        if (!scanner.hasNextLine()) {
                            message.setText("Login Failed. Try Again.");
                        }
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton createAccount = new JButton("Create an Account");
        createAccount.setFont(new Font("Arial", Font.BOLD, 14));
        createAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                try (FileWriter fileWriter = new FileWriter("src/main/java/database.csv", true)) {
                    fileWriter.write(username + "," + password + "\n");
                    message.setText("New Account Created");
                }
                catch(IOException ex) {
                    ex.printStackTrace();
                }
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
