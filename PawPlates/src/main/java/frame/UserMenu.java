package frame;/**
 * =============================================================================
 * File:           frame.UserMenu.java
 * Author:         Faith Ota, Dakota Hernandez
 * Created:        04/20/25
 * -----------------------------------------------------------------------------
 * Description:
 *   Creates the application menu bar with navigation items for home, tracking
 *   workouts, sleep, calories, and other actions; disposes the current frame
 *   and opens the selected page, passing along the chosen date.
 *
 * Dependencies:
 *   - javax.swing.JMenuBar
 *   - javax.swing.JMenu
 *   - javax.swing.JMenuItem
 *   - javax.swing.SwingUtilities
 *   - java.time.LocalDate
 *
 * Usage:
 *   // In a JFrame subclass:
 *   setJMenuBar(frame.UserMenu.addUserMenu());
 *
 * TODO:
 *   - open SetGoalPage(frame.SessionContext.getDate());
 *   - open RegistrationPage(frame.SessionContext.getDate());
 * =============================================================================
 */

import tracking.CalorieMacroPage;
import tracking.CreateExercise;
import tracking.SleepPage;

import javax.swing.*;
import javax.swing.SwingUtilities;

public class UserMenu {

    /**
     * Creates the application menu bar. All actions dispose the current window
     * and open the requested page, passing along the selected date.
     */
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public static JMenuBar addUserMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem home            = new JMenuItem("Home");
        JMenuItem trackWorkout    = new JMenuItem("Track workout.Workout");
        JMenuItem trackSleep      = new JMenuItem("Track Sleep");
        JMenuItem trackCals       = new JMenuItem("Track Calories");
        JMenuItem setGoal         = new JMenuItem("Set a Goal");
        JMenuItem registration    = new JMenuItem("Register for a Class");
        JMenuItem createExercise  = new JMenuItem("Create an workout.Exercise");

        // Home
        home.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
            current.dispose();
            new HomePage();
        });

        // Track workout.Workout
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
            // TODO: open SetGoalPage(frame.SessionContext.getDate());
        });

        // Register for a Class (to be implemented)
        registration.addActionListener(e -> {
            // TODO: open RegistrationPage(frame.SessionContext.getDate());
        });

        // Create workout.Exercise
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
