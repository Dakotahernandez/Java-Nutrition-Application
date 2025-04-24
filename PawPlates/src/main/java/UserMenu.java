import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import javax.swing.SwingUtilities;

public class UserMenu {

    /**
     * Creates the application menu bar. All actions dispose the current window
     * and open the requested page, passing along the selected date.
     */
    public static JMenuBar addUserMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem home            = new JMenuItem("Home");
        JMenuItem trackWorkout    = new JMenuItem("Track Workout");
        JMenuItem trackSleep      = new JMenuItem("Track Sleep");
        JMenuItem trackCals       = new JMenuItem("Track Calories");
        JMenuItem setGoal         = new JMenuItem("Set a Goal");
        JMenuItem registration    = new JMenuItem("Register for a Class");
        JMenuItem createExercise  = new JMenuItem("Create an Exercise");

        // Home
        home.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
            current.dispose();
            new HomePage();
        });

        // Track Workout
        trackWorkout.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
            current.dispose();
            new CreateExercise();
        });

        // Track Sleep (passes selected date)
        trackSleep.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
            current.dispose();
            new SleepPage(SessionContext.getDate());
        });

        // Track Calories (passes selected date)
        trackCals.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
            current.dispose();
            new CalorieMacroPage(SessionContext.getDate());
        });

        // Set a Goal (to be implemented)
        setGoal.addActionListener(e -> {
            // TODO: open SetGoalPage(SessionContext.getDate());
        });

        // Register for a Class (to be implemented)
        registration.addActionListener(e -> {
            // TODO: open RegistrationPage(SessionContext.getDate());
        });

        // Create Exercise
        createExercise.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
            current.dispose();
            new CreateExercise();
        });

        menu.add(home);
        menu.add(trackWorkout);
        menu.add(trackSleep);
        menu.add(trackCals);
        menu.add(setGoal);
        menu.add(registration);
        menu.add(createExercise);

        menuBar.add(menu);
        return menuBar;
    }
}
