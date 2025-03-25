import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.io.File;

public class LoginPage {

    public LoginPage() {
        JFrame frame = new JFrame();

        frame.setTitle("Login");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 15));

        JLabel username = new JLabel("  Username:");
        JTextField usernameField = new JTextField();

        JLabel password = new JLabel("  Password:");
        JTextField passwordField = new JTextField();

        JLabel message = new JLabel("");

        JButton login = new JButton("Login");
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

        panel.add(username);
        panel.add(usernameField);
        panel.add(password);
        panel.add(passwordField);
        panel.add(login);
        panel.add(message);

        frame.add(panel);
        frame.setVisible(true);
    }

}
