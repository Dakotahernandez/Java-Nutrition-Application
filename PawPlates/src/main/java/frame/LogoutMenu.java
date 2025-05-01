package frame; /**
 * =============================================================================
 * File:           frame.LogoutMenu.java
 * Author:         Faith Ota
 * Created:        04/20/25
 * -----------------------------------------------------------------------------
 * Description:
 *   Creates the application's logout menu bar to exit the program
 *
 * Dependencies:
 *   - javax.swing.*;
 *   - java.awt.event.ActionEvent;
 *   - java.awt.event.ActionListener;
 * Usage:
 *   Within a frame setup with the other menu bar in frame.MenuBars() to combine both
 *   menus as one object.
 *
 *   frame.TemplateFrame uses frame.MenuBars as a part of the template frame for the home
 *   page and tracking pages.
 * =============================================================================
 */
import javax.swing.*;

public class LogoutMenu {
    public LogoutMenu() { }
    /**
     * Description: a method to add a logout menu bar to templates
     *
     * @return JMenuBar item with logout options
     */
    public static JMenuBar addLogoutMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Logout");
        JMenuItem signOut = new JMenuItem("Sign Out");
        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem help = new JMenuItem("Help");

        JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);

        signOut.addActionListener(e -> {
            current.dispose();
            new LoginPage();
        });

        menu.add(signOut);
        menu.add(settings);
        menu.add(help);
        menuBar.add(menu);

        return menuBar;
    }
}

// NOTE: Mac has refactored this and instead just uses this code in the UserMenu class.
// This class is currently not needed. Refactor when you can.