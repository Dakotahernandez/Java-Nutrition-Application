package tracking.weightAndGoals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import frame.Theme;
/**
 * =============================================================================
 * File:           CalorieCalculatorPopup.java
 * Author:         Dakota Hernandez
 * Created:        05/07/25
 * -----------------------------------------------------------------------------
 * Description:
 *   A styled popup calorie calculator for estimating daily calorie needs
 *   based on user weight, goal (lose, maintain, gain), and desired weight
 *   change rate. Styled to match the global application theme.
 * =============================================================================
 */
public class CalorieCalculatorPopup {

    /**
     * Displays a pop-up calorie calculator near the specified component.
     * The calculator allows the user to input their weight in pounds,
     * select a goal (lose, maintain, or gain), and if applicable, choose
     * a weight change rate (0.5, 1, or 2 lb/week). It then calculates and
     * displays the recommended daily calorie intake.
     *
     * @param parent the component relative to which the popup will appear
     */
    public static void show(Component parent) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parent), "Calorie Calculator", Dialog.ModalityType.MODELESS);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(340, 280));
        panel.setBackground(Theme.BG_DARK);

        // Weight input
        JPanel weightPanel = createRowPanel();
        JLabel weightLabel = new JLabel("Weight (lbs):");
        JTextField weightField = new JTextField(5);
        styleLabel(weightLabel);
        styleField(weightField);
        weightPanel.add(weightLabel);
        weightPanel.add(weightField);
        panel.add(weightPanel);

        // Goal selection
        JPanel goalPanel = createRowPanel();
        styleLabel(goalPanel.add(new JLabel("Goal:")));
        JRadioButton loseBtn = styledRadio("Lose");
        JRadioButton maintainBtn = styledRadio("Maintain");
        JRadioButton gainBtn = styledRadio("Gain");
        maintainBtn.setSelected(true);
        ButtonGroup goalGroup = new ButtonGroup();
        goalGroup.add(loseBtn);
        goalGroup.add(maintainBtn);
        goalGroup.add(gainBtn);
        goalPanel.add(loseBtn);
        goalPanel.add(maintainBtn);
        goalPanel.add(gainBtn);
        panel.add(goalPanel);

        // Rate options
        JPanel ratePanel = createRowPanel();
        styleLabel(ratePanel.add(new JLabel("Rate:")));
        JRadioButton half = styledRadio("0.5 lb/week");
        JRadioButton one = styledRadio("1 lb/week");
        JRadioButton two = styledRadio("2 lb/week");
        half.setSelected(true);
        ButtonGroup rateGroup = new ButtonGroup();
        rateGroup.add(half);
        rateGroup.add(one);
        rateGroup.add(two);
        ratePanel.add(half);
        ratePanel.add(one);
        ratePanel.add(two);
        panel.add(ratePanel);

        // Result label
        JLabel resultLabel = new JLabel("Calories: ");
        resultLabel.setForeground(Theme.FG_LIGHT);
        resultLabel.setFont(Theme.NORMAL_FONT);
        panel.add(Box.createVerticalStrut(10));
        panel.add(resultLabel);

        // Calculate button
        JButton calcButton = new JButton("Calculate");
        styleButton(calcButton);
        calcButton.addActionListener(e -> {
            try {
                double weight = Double.parseDouble(weightField.getText());
                double maintenance = weight * 12.5;
                double adjustment = 0;

                // 1 lb fat = 3500 kcal
                if (gainBtn.isSelected() || loseBtn.isSelected()) {
                    if (half.isSelected()) adjustment = 3500.0 / 7 / 2;     // ~250
                    if (one.isSelected())  adjustment = 3500.0 / 7;         // 500
                    if (two.isSelected())  adjustment = 3500.0 / 7 * 2;      // 1000
                    if (loseBtn.isSelected()) adjustment = -adjustment;
                }

                int finalCals = (int) (maintenance + adjustment);
                resultLabel.setText("Calories: " + finalCals);
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid weight.");
            }
        });

        panel.add(Box.createVerticalStrut(5));
        panel.add(calcButton);

        // Toggle rate visibility based on goal
        ItemListener toggleRate = e -> {
            boolean show = gainBtn.isSelected() || loseBtn.isSelected();
            ratePanel.setVisible(show);
        };
        loseBtn.addItemListener(toggleRate);
        gainBtn.addItemListener(toggleRate);
        maintainBtn.addItemListener(toggleRate);
        toggleRate.itemStateChanged(null); // initial visibility

        dialog.add(panel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    private static JPanel createRowPanel() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Theme.BG_DARK);
        return row;
    }

    private static void styleLabel(Component label) {
        label.setForeground(Theme.FG_LIGHT);
        label.setFont(Theme.NORMAL_FONT);
    }

    private static void styleField(JTextField field) {
        field.setFont(Theme.NORMAL_FONT);
        field.setBackground(Theme.BG_LIGHTER);
        field.setForeground(Theme.FG_LIGHT);
        field.setCaretColor(Theme.FG_LIGHT);
        field.setBorder(BorderFactory.createLineBorder(Theme.BUTTON_BORDER));
    }

    private static JRadioButton styledRadio(String text) {
        JRadioButton btn = new JRadioButton(text);
        btn.setBackground(Theme.BG_DARK);
        btn.setForeground(Theme.FG_LIGHT);
        btn.setFont(Theme.NORMAL_FONT);
        return btn;
    }

    private static void styleButton(JButton button) {
        button.setFont(Theme.NORMAL_FONT);
        button.setBackground(Theme.BUTTON_BG);
        button.setForeground(Theme.BUTTON_FG);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Theme.BUTTON_BORDER));
    }
}
