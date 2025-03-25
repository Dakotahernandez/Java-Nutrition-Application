import javax.swing.*;
import java.awt.*;


public class HomePage {
    /*
    * GOAL: This is the home page that will show up POST-login that connects the other pages (tracking cals/workouts,
    * setting goals, & signing up for classes).
    * COMPLETED: created a HomePage object, menu bars for other pages & logout
    * IN-PROGRESS: making the buttons do something, adding the other windows, formatting the page, ...
     */

    //HomePage Constructor:
    public HomePage(){ setUp(); }

    // set up the main screen
    public static void setUp() {
        JFrame frame = new JFrame("PawPlates");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // open the window to a full screen
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());
        frame.setContentPane(contentPane);

        // Create a panel to hold both menu bars
        JPanel menuBarPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add userMenuBar to the left
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        menuBarPanel.add(userMenuBar(), gbc);

        // add logoutMenuBar to the right
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        menuBarPanel.add(logoutMenuBar(), gbc);

        // Add the menu bar panel to the frame
        contentPane.add(menuBarPanel, BorderLayout.NORTH);

        // add other content to the frame's center if needed
        JPanel mainPanel = new JPanel();
        mainPanel.add(new JLabel("HOME PAGE IN PROGRESS"));
        contentPane.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static JMenuBar userMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem trackWorkout = new JMenuItem("Track Workout");
        JMenuItem trackCals = new JMenuItem("Track Calories");
        JMenuItem setGoal = new JMenuItem("Set a Goal");
        JMenuItem registration = new JMenuItem("Register for a Class");

        menu.add(trackWorkout);
        menu.add(trackCals);
        menu.add(setGoal);
        menu.add(registration);

        menuBar.add(menu);
        return menuBar;
    }

    public static JMenuBar logoutMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Logout");
        JMenuItem signOut = new JMenuItem("Sign Out");
        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem help = new JMenuItem("Help");

        menu.add(signOut);
        menu.add(settings);
        menu.add(help);
        menuBar.add(menu);

        return menuBar;
    }

//    public static void main(String[] args){
//        new HomePage();
//    }
}
