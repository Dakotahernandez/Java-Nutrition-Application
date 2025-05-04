package frame; /**
 * =============================================================================
 * File:           frame.AnimatedProgressBar.java
 * Author:         Dakota Hernandez
 * Created:        04/20/25
 * -----------------------------------------------------------------------------
 * Description:
 *   A JProgressBar subclass that smoothly animates its value from the current
 *   position to a specified target value using a Swing Timer.
 *
 * Dependencies:
 *   - javax.swing.JProgressBar
 *   - javax.swing.Timer
 *   - java.awt.event.ActionListener
 *
 * Usage:
 *   // Create a bar ranging from 0 to 100
 *   frame.AnimatedProgressBar bar = new frame.AnimatedProgressBar(0, 100);
 *   // Animate it to 75%
 *   bar.animateTo(75);
 *
 * TODO:
 *
 * =============================================================================
 */


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimatedProgressBar extends JProgressBar {
    private Timer timer;
    private int targetValue;
    private static final int ANIMATION_DELAY_MS = 8;
    private static final int STEP             = 3;
    /**
     * Constructs an AnimatedProgressBar with the specified minimum and maximum values.
     *
     * @param min the minimum value of the progress bar
     * @param max the maximum value of the progress bar
     */
    public AnimatedProgressBar(int min, int max) {
        super(min, max);
        setStringPainted(true);
    }

    /**
     * Smoothly animate the bar value from its current position to newValue.
     */
    /**
     * Animates the progress bar from its current value to the given target value using smooth steps.
     *
     * @param newValue the target value to animate toward
     */

    public void animateTo(int newValue) {
        targetValue = newValue;
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        timer = new Timer(ANIMATION_DELAY_MS, new ActionListener() {
            /**
             * Called on each timer tick to update the progress bar value by a fixed step.
             * Stops the timer once the target value is reached.
             *
             * @param e the ActionEvent triggered by the timer
             */

            @Override
            public void actionPerformed(ActionEvent e) {
                int current = getValue();
                if (current < targetValue) {
                    setValue(Math.min(current + STEP, targetValue));
                } else if (current > targetValue) {
                    setValue(Math.max(current - STEP, targetValue));
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }
}