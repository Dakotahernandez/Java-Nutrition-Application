/**
 * =============================================================================
 * File:
 * Author:
 * Created:
 * -----------------------------------------------------------------------------
 * Description:
 *
 *
 * Dependencies:
 *
 *
 * Usage:
 *
 * =============================================================================
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutMenu {
    public LogoutMenu() { }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public static JMenuBar addLogoutMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Logout");
        JMenuItem signOut = new JMenuItem("Sign Out");
        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem help = new JMenuItem("Help");

        signOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
