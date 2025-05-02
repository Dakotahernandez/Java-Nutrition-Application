package tracking; /**
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
import java.awt.*;
import frame.*;

// This is the interface for creating an exercise
public class CreateExercise extends TemplateFrame {
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public CreateExercise() {
        addMenuBarPanel();

        JTextField nameField = new JTextField(15);
        addTextField("Exercise Name:", nameField, 0,0);

        JTextField focusField = new JTextField(15);
        addTextField("Focus:", focusField, 0,1);

        JTextField durationField = new JTextField(15);
        addTextField("Reps:", durationField, 0,2);

        JTextField repsField = new JTextField(15);
        addTextField("Duration(mins):", repsField, 0,3);

        JTextField calBurnedField = new JTextField(15);
        addTextField("Calories Burned:", calBurnedField, 0,4);

        JTextField descriptionField = new JTextField(15);
        addTextField("Description:", descriptionField, 0,5);

        JButton submitButton = new JButton("Create Workout");
        addButton(submitButton, 6);

        // Action listener for "Add Entry" Button
        submitButton.addActionListener(e -> {
            int calBurned;
            int duration;
            int reps;
            try{
                duration = Integer.parseInt(durationField.getText());
                reps = Integer.parseInt(repsField.getText());
                calBurned = Integer.parseInt(calBurnedField.getText());
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number for Duration, Reps, and Calories Burned.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Exercise exercise = new Exercise(nameField.getText(), focusField.getText(), calBurned, reps, duration, descriptionField.getText());


            //FIXME add exercise to the database


            JOptionPane.showMessageDialog(this,
                    "Exercise Name: " + exercise.getName() + "\n" +
                    "Focus: " + exercise.getFocus() +
                    "\n" + "Reps: " + exercise.getReps() +
                    "\n" + "Duration: " + exercise.getDuration() +
                    "\n" + "Calories Burned: " + exercise.getCaloriesBurned()+
                    "\n" + "Description: " + exercise.getDescription()

            , "Created Exercise", JOptionPane.INFORMATION_MESSAGE);
            nameField.setText("");
            focusField.setText("");
            descriptionField.setText("");
            calBurnedField.setText("");
            durationField.setText("");
            repsField.setText("");
        });

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new CreateExercise();
    }
}