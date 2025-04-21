import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginPage {
    public static User CURRENT_USER;

    public class Background extends JPanel {
        private BufferedImage background;

        public Background(String path) {
            try {
                background = ImageIO.read(new File(path));
            }
            catch (IOException e) {
                e.printStackTrace();
                background = null;
            }

            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.drawImage(background, 0, 0, getWidth(), getHeight(), this);
                g2d.dispose();
            }
        }
    }

    public LoginPage() {
        JFrame frame = new JFrame();
        UserDatabase database = new UserDatabase();

        // frame setup
        frame.setTitle("Login");
        frame.setSize(450, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Background panel = new Background("src/main/resources/background.jpg");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        // logo setup
        ImageIcon pawImage = new ImageIcon("src/main/resources/Paw.png");
        Image scaledPawImage = pawImage.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel pawImageLabel = new JLabel(new ImageIcon(scaledPawImage));
        pawImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(pawImageLabel, c);

        // welcome label
        JLabel welcome = new JLabel("Welcome to Paw Plates!");
        welcome.setFont(new Font("Arial", Font.BOLD, 20));
        welcome.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 2;
        panel.add(welcome, c);

        // username setup
        JLabel username = new JLabel("Username:");
        username.setFont(new Font("Arial", Font.PLAIN, 14));
        username.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.EAST;
        panel.add(username, c);

        JTextField usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usernameField, c);

        // password setup
        JLabel password = new JLabel("Password:");
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        password.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;
        panel.add(password, c);

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setEchoChar('•');
        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(passwordField, c);

        // message
        JLabel message = new JLabel("", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.PLAIN, 14));
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        panel.add(message, c);

        // login button
        JButton login = new JButton("Login");
        login.setFont(new Font("Arial", Font.BOLD, 14));
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        login.addActionListener(e -> {
            try {
                CURRENT_USER = new User(usernameField.getText(), passwordField.getText());
                database.loginUser(CURRENT_USER);
                frame.dispose();
                new HomePage();

            }
            catch (IllegalArgumentException ex) {
                message.setForeground(Color.RED);
                message.setText(ex.getMessage());
            }
        });
        panel.add(login, c);

        // create account
        JLabel createAccountLabel = new JLabel("OR");
        createAccountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(createAccountLabel, c);

        JButton createAccount = new JButton("Create an Account");
        createAccount.setFont(new Font("Arial", Font.BOLD, 14));
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        createAccount.addActionListener(e -> {
            try {
                CURRENT_USER = new User(usernameField.getText(), passwordField.getText());
                database.registerUser(CURRENT_USER);
                message.setForeground(Color.BLACK);
                message.setText("New account created with username and password.");
                usernameField.setText("");
                passwordField.setText("");
            }
            catch (IllegalArgumentException ex) {
                message.setForeground(Color.RED);
                message.setText(ex.getMessage());
            }
        });
        panel.add(createAccount, c);

        frame.add(panel);
        frame.setVisible(true);
    }

    private JPanel createAccountPage() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        return panel;
    }
}
