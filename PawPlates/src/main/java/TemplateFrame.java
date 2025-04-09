import javax.swing.*;
import java.awt.*;

class TemplateFrame extends JFrame {
    protected JPanel contentPane;
    protected JPanel menuBarPanel;
    protected JPanel contentPanel;
    protected JPanel topPanel;
    protected GridBagConstraints c;

    // Not suppose to be visible after construction
    // Has no title since it is a template
    public TemplateFrame(){
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        //Menu Bar panel
        menuBarPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //Creating the menu
        UserMenu userMenu = new UserMenu();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        menuBarPanel.add(userMenu.addUserMenu(), gbc);

        // Add logout menu bar to the right
        LogoutMenu logoutMenu = new LogoutMenu();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        menuBarPanel.add(logoutMenu.addLogoutMenu(), gbc);
        //Combine the menu panel into the topPanel
        topPanel = new JPanel(new BorderLayout());
        topPanel.add(menuBarPanel, BorderLayout.NORTH);
        contentPane.add(menuBarPanel, BorderLayout.NORTH);


        // Creates center panel
        contentPanel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

    }


    //These add functions add to the centerPanel

    // n is an int representing the order of the fields (Its vertical alignment start at 0)
    public void addTextField(String prompt, JTextField textField, int n){
        c.gridx = 0;
        c.anchor = GridBagConstraints.WEST;
        c.gridy = n;
        contentPanel.add(new JLabel(prompt), c);
        c.gridx = 1;
        contentPanel.add(textField, c);
    }
    public void addButton(JButton button, int n){
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = n;
        c.gridwidth = 2;
        contentPanel.add(button, c);
    }

    public void addProgressBar(JProgressBar progressBar, int setValue, JLabel progressLabel, String progressText){
        JPanel progressBarPanel = new JPanel();
        progressBarPanel.setLayout(new BoxLayout(progressBarPanel, BoxLayout.Y_AXIS));
        progressBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        progressBar.setValue(setValue);
        progressBar.setStringPainted(true); // shows % complete
        progressBar.setPreferredSize(new Dimension(400, 50));
        progressLabel = new JLabel(progressText);
        progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        progressBarPanel.add(progressBar);
        progressBarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        progressBarPanel.add(progressLabel);

        contentPane.remove(topPanel);
        topPanel.add(progressBarPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

    }
}
