
import javax.swing.*;
import java.awt.*;
/**
 * NOTES
 * WHats done and explanation for why i did certain things
 * Have set value for calories since we dont have a user logged in with a value
 * Has 2 menus that pop up has options like logging out or help tabs--
 * ----None of the buttons do anything
 * I added a bar across the tops that shows the percent of total
 * ----calories consumed so far updates evrytime there is a new entry
 *There is a commented out snippet of code i was using for quick testing witjout
 * changing the main or worrying about messing with other files
 *
 * Josh:
 *      Removed setUp
 *      Removed JFrame creation since CalorieMacroPage is a JFrame
 *      extended it to TemplateFrame
 *      Used TemplateFrame add functions to add menu, logout, progressBar, and Fields
 *      Cleaned up code
 */
public class CalorieMacroPage extends TemplateFrame{

    // Set daily calorie limit since there no db so far
    private static final int DAILY_LIMIT = 2000;

    // Track total calories consumed so far
    private static int totalCalsSoFar = 0;

    private static JProgressBar calorieProgressBar;
    private static JLabel progressLabel;

//    //for testing the code
//    public static void main(String[] args) {
//        new CalorieMacroPage();
//    }

    public CalorieMacroPage() {
        setTitle("Calorie/Macro Tracker");

        calorieProgressBar = new JProgressBar(0, DAILY_LIMIT);
        addProgressBar(calorieProgressBar, totalCalsSoFar, progressLabel, getProgressText());

        JTextField foodNameField = new JTextField(15);
        addTextField("Food Name:", foodNameField,0);

        JTextField caloriesField = new JTextField(15);
        addTextField("Calories:", caloriesField,1);

        JTextField carbsField = new JTextField(15);
        addTextField("Carbohydrates (g):", carbsField,2);

        JTextField proteinField = new JTextField(15);
        addTextField("Protein (g):", proteinField,3);

        JTextField fatField = new JTextField(15);
        addTextField("Fat (g):", fatField,4);

        JButton submitButton = new JButton("Add Entry");
        addButton(submitButton, 5);

        // Action listener for "Add Entry"
        submitButton.addActionListener(e -> {
            String foodName = foodNameField.getText();
            String calStr = caloriesField.getText();
            String carbs = carbsField.getText();
            String protein = proteinField.getText();
            String fat = fatField.getText();

            // Parse and update total calories so far
            int cals = 0;
            try {
                cals = Integer.parseInt(calStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number for calories.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            totalCalsSoFar += cals;

            // Update the progress bar
            calorieProgressBar.setValue(Math.min(totalCalsSoFar, DAILY_LIMIT));
            progressLabel.setText(getProgressText());

            // Display the entered values in a dialog
            JOptionPane.showMessageDialog(this,
                    "Entry Added:" +
                            "\nFood: " + foodName +
                            "\nCalories: " + calStr +
                            "\nCarbohydrates: " + carbs + " g" +
                            "\nProtein: " + protein + " g" +
                            "\nFat: " + fat + " g"
            );

            // Clear fields
            foodNameField.setText("");
            caloriesField.setText("");
            carbsField.setText("");
            proteinField.setText("");
            fatField.setText("");
        });

        // Add the form panel to the center of the content pane
        contentPane.add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Helper method to display progress in text form
    private static String getProgressText() {
        if (totalCalsSoFar <= DAILY_LIMIT) {
            return String.format("Calories so far: %d / %d", totalCalsSoFar, DAILY_LIMIT);
        } else {
            int overBy = totalCalsSoFar - DAILY_LIMIT;
            return String.format("Calories so far: %d (Over by %d)", totalCalsSoFar, overBy);
        }
    }
}