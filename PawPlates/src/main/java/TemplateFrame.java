import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.UIManager;
import java.awt.*;

class TemplateFrame extends JFrame {
    protected JPanel contentPane;
    protected JPanel menuBarPanel;
    protected JPanel contentPanel;
    protected JPanel topPanel;
    protected GridBagConstraints c;

    // Not supposed to be visible after construction
    // Has no title since it is a template
    public TemplateFrame(){
        // Maximize window and set default close operation
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set default caret color for all text-based components
        UIManager.put("TextField.caretForeground", new ColorUIResource(Theme.FG_LIGHT));
        UIManager.put("TextArea.caretForeground", new ColorUIResource(Theme.FG_LIGHT));
        UIManager.put("PasswordField.caretForeground", new ColorUIResource(Theme.FG_LIGHT));

        // Create the content pane with BorderLayout and assign theme background
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Theme.BG_DARK);
        setContentPane(contentPane);

        // Menu Bar panel setup using GridBagLayout
        menuBarPanel = new JPanel(new GridBagLayout());
        menuBarPanel.setBackground(Theme.BG_DARK);
        GridBagConstraints gbc = new GridBagConstraints();

        // Creating the user menu (assumed to be themed already)
        UserMenu userMenu = new UserMenu();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        menuBarPanel.add(userMenu.addUserMenu(), gbc);

        // Add the logout menu to the right (assumed to be themed already)
        LogoutMenu logoutMenu = new LogoutMenu();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        menuBarPanel.add(logoutMenu.addLogoutMenu(), gbc);

        // Combine the menu panel into the topPanel using BorderLayout
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Theme.BG_DARK);
        topPanel.add(menuBarPanel, BorderLayout.NORTH);
        contentPane.add(topPanel, BorderLayout.NORTH);

        // Create the center content panel for additional components
        contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Theme.BG_DARK);
        c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(contentPanel, BorderLayout.CENTER);
    }

    // Add function to add a text field with a prompt in the content panel
    public void addTextField(String prompt, JTextField textField, int n){
        c.gridx = 0;
        c.anchor = GridBagConstraints.WEST;
        c.gridy = n;
        JLabel label = new JLabel(prompt);
        label.setForeground(Theme.FG_LIGHT);
        contentPanel.add(label, c);
        c.gridx = 1;
        // Style the text field using theme colors
        textField.setBackground(Theme.BG_DARKER);
        textField.setForeground(Theme.FG_LIGHT);
        // Ensure the caret is visible
        textField.setCaretColor(Theme.FG_LIGHT);
        contentPanel.add(textField, c);
    }

    // Add function to add a button in the content panel
    public void addButton(JButton button, int n){
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = n;
        c.gridwidth = 2;
        // Style the button using theme colors
        button.setBackground(Theme.BUTTON_BG);
        button.setForeground(Theme.BUTTON_FG);
        contentPanel.add(button, c);
    }

    // Add a progress bar to the topPanel. The progress bar and its label are styled using the Theme.
    public void addProgressBar(JProgressBar progressBar, int setValue, JLabel progressLabel, String progressText){
        JPanel progressBarPanel = new JPanel();
        progressBarPanel.setLayout(new BoxLayout(progressBarPanel, BoxLayout.Y_AXIS));
        progressBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        progressBarPanel.setBackground(Theme.BG_DARK);

        progressBar.setValue(setValue);
        progressBar.setStringPainted(true); // Shows the percentage complete
        progressBar.setPreferredSize(new Dimension(400, 50));

        // Create and style the progress label
        progressLabel = new JLabel(progressText);
        progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressLabel.setForeground(Theme.FG_LIGHT);
        progressLabel.setBackground(Theme.BG_DARK);
        progressLabel.setOpaque(true);

        progressBarPanel.add(progressBar);
        progressBarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        progressBarPanel.add(progressLabel);

        // Remove any existing topPanel components and add the progress bar panel to the topPanel
        contentPane.remove(topPanel);
        topPanel.add(progressBarPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);
    }
}
