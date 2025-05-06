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

import tracking.*;
import tracking.Food.CalorieMacroPage;
import tracking.weightAndGoals.SetGoalPage;
import tracking.weightAndGoals.SleepPage;

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
        JMenuItem trackWorkout    = new JMenuItem("Track Workout");
        JMenuItem createWorkout    = new JMenuItem("Create Workout");

        JMenuItem trackSleep      = new JMenuItem("Track Sleep");
        JMenuItem trackCals       = new JMenuItem("Track Calories");
        JMenuItem setGoal         = new JMenuItem("Set a Goal");
        JMenuItem registration    = new JMenuItem("Register for a Class");
        JMenuItem createExercise  = new JMenuItem("Create an Exercise");

        // Home
        home.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
            new HomePage();
            current.dispose();
        });

        // Track workout.Workout
        trackWorkout.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
            new TrackWorkouts();
            current.dispose();
        });

        // Track workout.Workout
        createWorkout.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
            new CreateWorkoutPage();
            current.dispose();
        });

        // Track Sleep (passes selected date)
        trackSleep.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
            new SleepPage(SessionContext.getDate());
            current.dispose();
        });

        // Track Calories (passes selected date)
        trackCals.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
            new CalorieMacroPage(SessionContext.getDate());
            current.dispose();
        });

        // Set a Goal (to be implemented)
        setGoal.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
            new SetGoalPage();
            current.dispose();
        });

        // Register for a Class (to be implemented)
        registration.addActionListener(e -> {
            // TODO: open RegistrationPage(frame.SessionContext.getDate());
        });

        // Create workout.Exercise
        createExercise.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
            new CreateExercise();
            current.dispose();
        });

        menu.add(home);
        menu.add(trackWorkout);
        menu.add(createWorkout);
        menu.add(trackSleep);
        menu.add(trackCals);
        menu.add(setGoal);
        menu.add(registration);
        menu.add(createExercise);

        menuBar.add(menu);
        menuBar.add(Box.createHorizontalGlue());

        // USER MENU
        JMenu userMenu = new JMenu(LoginPage.CURRENT_USER.getUsername());

        JMenuItem profile = new JMenuItem("Profile");
        JMenuItem logout = new JMenuItem("Sign Out");

        // profile
        profile.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
            // new Profile
            current.dispose();
        });

        // logout
        logout.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
            new LoginPage();
            LoginPage.CURRENT_USER = null;
            current.dispose();
        });

        userMenu.add(profile);
        userMenu.add(logout);

        menuBar.add(userMenu);

        return menuBar;
    }
}
