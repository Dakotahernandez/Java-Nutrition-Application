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

        JTextField exerciseName = new JTextField(15);
        addTextField("Exercise Name:", exerciseName, 0,0);

        JTextField focus = new JTextField(15);
        addTextField("Focus:", focus, 0,1);

        JTextField durationReps = new JTextField(15);
        addTextField("Duration(mins)/Reps:", durationReps, 0,2);

        JTextField caloriesBurned = new JTextField(15);
        addTextField("Calories Burned:", caloriesBurned, 0,3);

        JTextField description = new JTextField(15);
        addTextField("Description:", description, 0,4);

        JButton submitButton = new JButton("Create Workout");
        addButton(submitButton, 6);

        // Action listener for "Add Entry" Button
        submitButton.addActionListener(e -> {
            int calBurned;
            int durReps;
            try{
                durReps = Integer.parseInt(durationReps.getText());
                calBurned = Integer.parseInt(caloriesBurned.getText());
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number for Duration/Reps and Calories Burned.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Exercise exercise = new Exercise(exerciseName.getText(), focus.getText(), calBurned,durReps, description.getText());


            //FIXME add exercise to the database

            String message = "Created Exercise";

            JOptionPane.showMessageDialog(this, message + "\n" +
                    "workout.Exercise Name: " + exercise.getName() + "\n" +
                    "Focus: " + exercise.getFocus() +
                    "\n" + "Calories Burned: " + exercise.getCaloriesBurned()+
                    "\n" + "Duration/Reps: " + exercise.getDurationReps() +
                    "\n" + "Description: " + exercise.getDescription()

            );
            exerciseName.setText("");
            focus.setText("");
            description.setText("");
            caloriesBurned.setText("");
            durationReps.setText("");
        });

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new CreateExercise();
    }
}