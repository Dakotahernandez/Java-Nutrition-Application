/**
 * =============================================================================
 * File:           LogoutMenu.java
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
 *   Within a frame setup with the other menu bar in MenuBars() to combine both
 *   menus as one object.
 *
 *   TemplateFrame uses MenuBars as a part of the template frame for the home
 *   page and tracking pages.
 * =============================================================================
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutMenu {
    public LogoutMenu() { }
    /**
     * Description:
     *
     * @param
     * @return
     *   JMenuBar item with logout options
     * @throws
     */
    public static JMenuBar addLogoutMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Logout");
        JMenuItem signOut = new JMenuItem("Sign Out");
        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem help = new JMenuItem("Help");

        JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);

        signOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current.dispose();
                new LoginPage();
            }
        });

        menu.add(signOut);
        menu.add(settings);
        menu.add(help);
        menuBar.add(menu);

        return menuBar;
    }
}
