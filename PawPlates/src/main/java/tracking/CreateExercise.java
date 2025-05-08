/**
 * =============================================================================
 * File: CreateExercise.java
 * Author: Joshua Carroll
 * Created: 3/8/2025
 * -----------------------------------------------------------------------------
 * Description:
 * GUI form that allows users to create a new Exercise entry by entering
 * name, focus, reps, duration, calories burned, and a description. The
 * data is saved to the ExerciseDatabase upon submission.
 *
 * Dependencies:
 * - javax.swing.*
 * - java.awt.*
 * - tracking.Exercise
 * - tracking.ExerciseDatabase
 * - frame.TemplateFrame
 * - frame.LoginPage
 *
 * Usage:
 * new CreateExercise(); // Launches the form to create and store an exercise
 * =============================================================================
 */
package tracking;
import javax.swing.*;
import java.awt.*;
import frame.*;

public class CreateExercise extends TemplateFrame {
    private static final ExerciseDatabase db = new ExerciseDatabase();

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
        addTextField("Exercise Name:", nameField, 0, 0);

        JTextField focusField = new JTextField(15);
        addTextField("Focus:", focusField, 0, 1);

        JTextField durationField = new JTextField(15);
        addTextField("Reps:", durationField, 0, 2);

        JTextField repsField = new JTextField(15);
        addTextField("Duration(mins):", repsField, 0, 3);

        JTextField calBurnedField = new JTextField(15);
        addTextField("Calories Burned:", calBurnedField, 0, 4);

        JTextField descriptionField = new JTextField(15);
        addTextField("Description:", descriptionField, 0, 5);

        JButton submitButton = new JButton("Create Workout");
        addButton(submitButton, 6);

        submitButton.addActionListener(e -> {
            int calBurned;
            int duration;
            int reps;
            try {
                duration = Integer.parseInt(durationField.getText());
                reps = Integer.parseInt(repsField.getText());
                calBurned = Integer.parseInt(calBurnedField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number for Duration, Reps, and Calories Burned.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Exercise exercise = new Exercise(nameField.getText(), focusField.getText(), calBurned, reps, duration, descriptionField.getText());


            int generatedId = db.saveExercise(exercise);
            if (generatedId == -1) {
                System.out.println("Not Saved Exercise");
            } else {
                exercise.setId(generatedId);
            }

            JOptionPane.showMessageDialog(this,
                    "Exercise Name: " + exercise.getName() + "\n" +
                            "Focus: " + exercise.getFocus() +
                            "\n" + "Reps: " + exercise.getReps() +
                            "\n" + "Duration: " + exercise.getDuration() +
                            "\n" + "Calories Burned: " + exercise.getCaloriesBurned() +
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
}