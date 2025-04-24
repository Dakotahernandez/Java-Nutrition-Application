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

public class RecordWeight extends TemplateFrame{
    private static final int GOAL_WEIGHT = 145;
    private static final int START_WEIGHT = 165;
    private static int curWeight = START_WEIGHT;
    private static JProgressBar weightProgress;
    private static JLabel progressLabel;

    // need to add menu bar panel, set the progressBar at the northPanel.south
    // menu bar is set at northPanel.north when its function is used

    public RecordWeight() {
        weightProgress = new JProgressBar(0, GOAL_WEIGHT);
        progressLabel = new JLabel(getProgressText());

        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        progressPanel.add(progressLabel, BorderLayout.NORTH);
        progressPanel.add(weightProgress, BorderLayout.CENTER);

        JTextField newWeight = new JTextField(15);
        addTextField("Current Weight:", newWeight, 0,0);

        JButton confirm = new JButton("Confirm");
        addButton(confirm, 5);

        confirm.addActionListener(e -> {
            String inputWeight = newWeight.getText();
            int weight = Integer.parseInt(inputWeight);

            if(weight > GOAL_WEIGHT && weight < curWeight){
                JOptionPane.showMessageDialog(this,
                        "Great work, you're making progress towards your weight goal");
            } else if(weight <= GOAL_WEIGHT){
                JOptionPane.showMessageDialog(this,
                        "Congrats, you've met your weight goal!");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Uh oh, double check your plan to stay on track");
            }

            curWeight = weight;
            weightProgress.setValue(Math.min(curWeight, GOAL_WEIGHT));
            progressLabel.setText(getProgressText());
        });

        add(centerPanel, BorderLayout.CENTER);
        add(progressPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    private static String getProgressText() {
        return String.format("Weight Goal Progress: %d → %d lbs", curWeight, GOAL_WEIGHT);
    }

    public static void main(String[] args) { new RecordWeight(); }
}
