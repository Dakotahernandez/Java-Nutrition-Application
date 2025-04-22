import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.UIManager;
import java.awt.*;

class TemplateFrame extends JFrame {
    protected JPanel centerPanel; //gridBagLayout
    protected JPanel northPanel;  //BorderLayout
    protected GridBagConstraints c;

    // not used panels currently. If need more flexibility, use these panels. Keep the ui easy to understand
    // Our frames will have 5 panels used in borderLayout. If used, label what layout it has
//    protected JPanel southPanel;
//    protected JPanel westPanel;
//    protected JPanel eastPanel;

    public TemplateFrame(){
        // Maximize window and set default close operation
        centerPanel = new JPanel(new GridBagLayout());
        northPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Theme.BG_DARK);
        northPanel.setBackground(Theme.BG_DARK);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set default caret color for all text-based components
        UIManager.put("TextField.caretForeground", new ColorUIResource(Theme.FG_LIGHT));
        UIManager.put("TextArea.caretForeground", new ColorUIResource(Theme.FG_LIGHT));
        UIManager.put("PasswordField.caretForeground", new ColorUIResource(Theme.FG_LIGHT));

        setBackground(Theme.BG_DARK);
        setLayout(new BorderLayout());
        c = new GridBagConstraints();
    }


    public void addMenuBarPanel(){
        // Menu Bar panel setup using GridBagLayout
        JPanel menuBarPanel = new JPanel(new GridBagLayout());
        menuBarPanel.setBackground(Theme.BG_DARK);

        // Creating the user menu (assumed to be themed already)
        UserMenu userMenu = new UserMenu();
        c.insets = new Insets (0,0,0,0);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        menuBarPanel.add(userMenu.addUserMenu(), c);

        // Add the logout menu to the right (assumed to be themed already)
        LogoutMenu logoutMenu = new LogoutMenu();
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        menuBarPanel.add(logoutMenu.addLogoutMenu(), c);

        // Combine the menu panel into the topPanel using BorderLayout
        northPanel.setBackground(Theme.BG_DARK);
        northPanel.add(menuBarPanel, BorderLayout.NORTH);
        add(northPanel, BorderLayout.NORTH);
    }

    // Add function to add a text field with a prompt in the centerPanel
    public void addTextField(String prompt, JTextField textField, int x, int y){
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = x;
        c.anchor = GridBagConstraints.WEST;
        c.gridy = y;
        JLabel label = new JLabel(prompt);
        label.setForeground(Theme.FG_LIGHT);
        centerPanel.add(label, c);
        c.gridx = 1;
        // Style the text field using theme colors
        textField.setBackground(Theme.BG_DARKER);
        textField.setForeground(Theme.FG_LIGHT);
        // Ensure the caret is visible
        textField.setCaretColor(Theme.FG_LIGHT);
        centerPanel.add(textField, c);
    }

    // Add function to add a button in the content panel
    public void addButton(JButton button, int n){
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = n;
        c.gridwidth = 2;
        // Style the button using theme colors
        button.setBackground(Theme.BUTTON_BG);
        button.setForeground(Theme.BUTTON_FG);
        centerPanel.add(button, c);
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
        northPanel.add(progressBarPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);
    }
}
