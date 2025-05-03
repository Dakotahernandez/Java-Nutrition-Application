package tracking;

import frame.HomePage;
import frame.TemplateFrame;
import tracking.Food.CalorieMacroPage;

import javax.swing.*;


/**
 * =============================================================================
 * File: tracking.SetGoalPage.java
 * Author: Faith Ota
 * Created: 05/01/2025
 * -----------------------------------------------------------------------------
 * Description:
 *   Application's page to set goals & update calorie bar, weight graph, and sleep progress
 *   bars/graphs
 *
 * Dependencies:
 *   - frame.HomePage
 *   - frame.TemplateFrame
 *   - javax.swing.*
 *
 * Usage:
 *   Display the goals page and update the calorie, sleep, and weight graphs (if saved)
 * =============================================================================
 */

public class SetGoalPage extends TemplateFrame {

    public SetGoalPage(){
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
