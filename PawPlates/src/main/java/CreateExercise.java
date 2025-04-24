/**
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

// This is the interface for creating an exercise
public class CreateExercise extends TemplateFrame {

    public CreateExercise() {
        addMenuBarPanel();

        JTextField exerciseName = new JTextField(15);
        addTextField("Exercise Name:", exerciseName, 0,0);

        JTextField focus = new JTextField(15);
        addTextField("Focus:", focus, 0,1);

        JTextField description = new JTextField(15);
        addTextField("Description:", description, 0,2);

        JButton submitButton = new JButton("Add Entry");
        addButton(submitButton, 5);

        // Action listener for "Add Entry" Button
        submitButton.addActionListener(e -> {
            Exercise exercise = new Exercise(exerciseName.getText(), focus.getText(), description.getText());
            int i = exercise.writeCSV();

            String message = "";
            if(i==1) {
                message = "Exercise Created Successfully";
            }
            else if(i==0){
                message = "Exercise Already Exists";
            }
            else{
                message = "Exercise could not be added";
            }
            JOptionPane.showMessageDialog(this, message + "\n" +
                    "Exercise Name: " + exerciseName.getText() + "\n" +
                    "Focus: " + focus.getText() + "\n" + "Description: " + description.getText()

            );
            exerciseName.setText("");
            focus.setText("");
            description.setText("");
        });

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new CreateExercise();
    }
}