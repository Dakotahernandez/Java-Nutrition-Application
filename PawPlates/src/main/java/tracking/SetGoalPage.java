package tracking;

import frame.TemplateFrame;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * =============================================================================
 * File: tracking.SetGoalPage.java
 * Author: Faith Ota
 * Created: 05/01/2025
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

public class SetGoalPage extends TemplateFrame {
    private LocalDate date; //this will be a start goal

    public SetGoalPage(LocalDate date){
        this.date = date;
        addMenuBarPanel();
        setTitle("Set A Goal");

        JTextField endDateField = new JTextField(12);
        addTextField("When do you want to achieve this goal? (MM/DD/YYYY)", endDateField,0, 0);

        JTextField startWeight = new JTextField(10);
        addTextField("What is your goal weight (lbs)?", startWeight, 0, 1);

        JTextField goalWeight = new JTextField(10);
        addTextField("What is your starting weight (lbs)?", goalWeight, 0, 2);

        JTextField goalCal = new JTextField(5);
        addTextField("How many calories per day do you want to set?", goalCal, 0, 3);

        JTextField goalSleep = new JTextField(3);
        addTextField("What is your goal sleep time per week?", goalSleep, 0, 4);

        JButton setGoal = new JButton("Set Goal");
        addButton(setGoal, 5);
        setGoal.addActionListener(e->{
            try{
                RecordWeight.setStartWeight(Integer.parseInt(startWeight.getText()));
                RecordWeight.setGoalWeight(Integer.parseInt(goalWeight.getText()));
                CalorieMacroPage.setDailyLimit(Integer.parseInt(goalCal.getText()));
                SleepPage.setWeeklyGoal(Integer.parseInt(goalSleep.getText()));
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Please enter valid numbers");
            }
        });

        setVisible(true);
    }
}
