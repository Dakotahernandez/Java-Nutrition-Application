import javax.swing.*;
import java.awt.*;

abstract class TemplateFrame extends JFrame {
    public TemplateFrame(){
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        //Menu Bar panel
        JPanel menuBarPanel = new JPanel(new GridBagLayout());
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
        setVisible(true);
    }
}
