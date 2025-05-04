package tracking.Food; /**
 * =============================================================================
 * File:           tracking.Food.FoodEntryDialog.java
 * Author:         Dakota Hernandez
 * Created:        04/20/25
 * -----------------------------------------------------------------------------
 * Description:
 *   A modal dialog window allowing users to add or edit a food entry.
 *   Fields include food name, calories, macros, notes, and meal type.
 *
 * Dependencies:
 *   - javax.swing.*
 *   - java.awt.*
 *   - tracking.Food.FoodEntry
 *
 * Usage:
 *   tracking.Food.FoodEntryDialog dialog = new tracking.Food.FoodEntryDialog(parentFrame, foodEntry);
 *   dialog.setVisible(true);
 *
 * TODO:
 *   - Improve field validations (e.g., calories must be a positive number).
 * =============================================================================
 */

import javax.swing.*;
import java.awt.*;
import frame.*;
/**
 * A modal dialog window allowing the user to input or edit a food entry.
 * Fields include name, calories, macros, notes, and meal type.
 */
public class FoodEntryDialog extends JDialog {
    private final JTextField[] fields = new JTextField[7];
    private JComboBox<String> mealComboBox;
    private boolean saved = false;
    /**
     * Constructs the dialog UI and pre-populates fields if a record is provided.
     *
     * @param parent the parent frame that owns this dialog
     * @param record an existing FoodEntry to edit, or null to create a new one
     */
    public FoodEntryDialog(Frame parent, FoodEntry record) {
        super(parent, "Food Entry Form", true);
        setLayout(new GridLayout(9, 2, 5, 5));

        Color dialogBg = Theme.BG_DARK;
        Color fieldBg  = new Color(60, 60, 60);
        Color fgLight  = Theme.FG_LIGHT;

        getContentPane().setBackground(dialogBg);

        String[] labels = {
                "Food Name", "Calories", "Protein", "Carbs", "Fats", "Fiber", "Notes"
        };
        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setForeground(fgLight);
            lbl.setFont(Theme.NORMAL_FONT);
            add(lbl);

            fields[i] = new JTextField();
            fields[i].setFont(Theme.NORMAL_FONT);
            fields[i].setBackground(fieldBg);
            fields[i].setForeground(fgLight);
            add(fields[i]);
        }

        JLabel mealLabel = new JLabel("Meal Type");
        mealLabel.setForeground(fgLight);
        mealLabel.setFont(Theme.NORMAL_FONT);
        add(mealLabel);

        mealComboBox = new JComboBox<>(new String[]{"Breakfast", "Lunch", "Dinner"});
        mealComboBox.setFont(Theme.NORMAL_FONT);
        mealComboBox.setBackground(fieldBg);
        mealComboBox.setForeground(fgLight);
        add(mealComboBox);

        if (record != null) {
            fields[0].setText(record.getFoodName());
            fields[1].setText(String.valueOf(record.getCalories()));
            fields[2].setText(record.getProtein());
            fields[3].setText(record.getCarbs());
            fields[4].setText(record.getFats());
            fields[5].setText(record.getFiber());
            fields[6].setText(record.getNotes());
            mealComboBox.setSelectedItem(record.getMealType());
        }

        JButton saveBtn   = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        saveBtn.setFont(Theme.NORMAL_FONT);
        saveBtn.setBackground(Theme.BUTTON_BG);
        saveBtn.setForeground(Theme.BUTTON_FG);
        cancelBtn.setFont(Theme.NORMAL_FONT);
        cancelBtn.setBackground(Theme.BUTTON_BG);
        cancelBtn.setForeground(Theme.BUTTON_FG);

        saveBtn.addActionListener(e -> {
            if (fields[0].getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Food Name cannot be blank.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            saved = true;
            setVisible(false);
        });

        cancelBtn.addActionListener(e -> setVisible(false));

        add(saveBtn);
        add(cancelBtn);

        pack();
        setLocationRelativeTo(parent);
    }
    /**
     * Returns whether the user clicked the "Save" button.
     *
     * @return true if saved, false otherwise
     */
    public boolean isSaved() {
        return saved;
    }
    /**
     * Constructs a FoodEntry object from the current form values.
     *
     * @return a new FoodEntry filled with the form's input
     */
    public FoodEntry getRecord() {
        String food     = fields[0].getText().trim();
        int    calories = parseIntSafe(fields[1].getText());
        String protein  = defaultToZero(fields[2].getText());
        String carbs    = defaultToZero(fields[3].getText());
        String fats     = defaultToZero(fields[4].getText());
        String fiber    = defaultToZero(fields[5].getText());
        String notes    = fields[6].getText().trim();
        String mealType = (String) mealComboBox.getSelectedItem();
        return new FoodEntry(food, calories, protein, carbs, fats, fiber, notes, mealType);
    }
    /**
     * Safely parses an integer from a string. Returns 0 if parsing fails.
     *
     * @param text the string to parse
     * @return the parsed integer, or 0 if invalid
     */
    private int parseIntSafe(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    /**
     * Converts an empty or null string to "0".
     *
     * @param text input string
     * @return "0" if empty, or trimmed text
     */
    private String defaultToZero(String text) {
        return (text == null || text.trim().isEmpty()) ? "0" : text.trim();
    }
}
