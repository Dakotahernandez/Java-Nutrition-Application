package tracking;

import frame.HomePage;
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

        JTextField startWeight = new JTextField(10);
        addTextField("What is your goal weight (lbs)?", startWeight, 0, 0);

        JTextField goalWeight = new JTextField(10);
        addTextField("What is your starting weight (lbs)?", goalWeight, 0, 1);

        JTextField goalCal = new JTextField(5);
        addTextField("How many calories per day do you want to set?", goalCal, 0, 2);

        JTextField goalSleep = new JTextField(3);
        addTextField("What is your goal sleep time per week?", goalSleep, 0, 3);

        JButton setGoal = new JButton("Set Goal");
        addButton(setGoal, 4);
        setGoal.addActionListener(e->{
            try{
                int opt = JOptionPane.showConfirmDialog(this, "Save your goal?", "Save Goal", JOptionPane.YES_NO_OPTION);
                if(opt == JOptionPane.YES_OPTION){
                    RecordWeight.setStartWeight(Integer.parseInt(startWeight.getText()));
                    RecordWeight.setGoalWeight(Integer.parseInt(goalWeight.getText()));
                    CalorieMacroPage.setDailyLimit(Integer.parseInt(goalCal.getText()));
                    SleepPage.setWeeklyGoal(Integer.parseInt(goalSleep.getText()));
                    this.dispose();
                    new HomePage();
                }
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Please enter valid numbers", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
