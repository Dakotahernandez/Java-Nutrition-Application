import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * GOAL: This is the home page that will show up POST-login that connects the other pages (tracking cals/workouts,
 * setting goals, & signing up for classes).
 * COMPLETED: created a HomePage object, menu bars for other pages & logout (separate classes)
 * IN-PROGRESS: making the buttons do something, adding the other windows, formatting the page, ...
 */

public class HomePage extends JFrame{
    /*
     * GOAL: This is the home page that will show up POST-login that connects the other pages (tracking cals/workouts,
     * setting goals, & signing up for classes).
     * COMPLETED: created a HomePage object, menu bars for other pages & logout (separate classes)
     * IN-PROGRESS: making the buttons do something, adding the other windows, formatting the page, ...
     */

    //HomePage Constructor:
    public HomePage(){ setUp(); }

    // set up the main screen
    public static void setUp() {
        JFrame frame = new JFrame("PawPlates");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // open the window to a full screen
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());
        frame.setContentPane(contentPane);

        // Create a panel to hold both menu bars
        JPanel menuBarPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add userMenuBar to the left
        UserMenu userMenu = new UserMenu();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        menuBarPanel.add(userMenu.addUserMenu(), gbc);

        // add logoutMenuBar to the right
        LogoutMenu logoutMenu = new LogoutMenu();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        menuBarPanel.add(logoutMenu.addLogoutMenu(), gbc);

        // Add the menu bar panel to the frame
        contentPane.add(menuBarPanel, BorderLayout.NORTH);

        // add other content to the frame's center if needed
        JPanel mainPanel = new JPanel();
        mainPanel.add(new JLabel("HOME PAGE IN PROGRESS"));
        contentPane.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }


//    public static void main(String[] args){
//        new HomePage();
//    }
}