import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * PURPOSE: created a menu bar object so that all you have to do is create a UserMenu object &
 * call the addUserMenu method to create the menu bar
 * COMPLETED: connected the Home & Track Calories pages
 * TO-DO: connect track workout/set goal pages, stop having multiple windows open
 */

public class UserMenu {

    public UserMenu() { }

    public static JMenuBar addUserMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem home = new JMenuItem("Home");
        JMenuItem trackWorkout = new JMenuItem("Track Workout");
        JMenuItem trackCals = new JMenuItem("Track Calories");
        JMenuItem trackSleep = new JMenuItem("Track Sleep");
        JMenuItem setGoal = new JMenuItem("Set a Goal");
        JMenuItem registration = new JMenuItem("Register for a Class");
        JMenuItem createExercise = new JMenuItem("Create an Exercise");


        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePage();
            }
        });

        trackCals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CalorieMacroPage();
            }
        });
        trackSleep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SleepPage();
            }
        });
        createExercise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateExercise();
            }
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
