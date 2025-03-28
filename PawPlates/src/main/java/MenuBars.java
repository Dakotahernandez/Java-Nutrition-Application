import javax.swing.*;
import java.awt.*;

public class MenuBars {
    public static JPanel addMenuBars(JPanel contentPane, GridBagConstraints gbc){
        //Create a panel for the menu bars
        JPanel menuBarPanel = new JPanel(new GridBagLayout());
        //GridBagConstraints gbc = new GridBagConstraints();

        // Add menu bar to the left
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
        // Add the menu bar panel to the frame
        contentPane.add(menuBarPanel, BorderLayout.NORTH);
        return contentPane;
    }
}
