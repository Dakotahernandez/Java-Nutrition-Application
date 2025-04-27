package frame; /**
 * =============================================================================
 * File:           frame.TemplateFrame.java
 * Authors:         Dakota Hernandez, Joshua Carrol
 * Created:        04/20/25
 * -----------------------------------------------------------------------------
 * Description:
 *   A base JFrame subclass that provides a dark-themed template frame with
 *   a menu bar, text field and button helpers, and optional animated
 *   progress bar integration.
 *
 * Dependencies:
 *   - javax.swing.JFrame
 *   - javax.swing.JPanel
 *   - javax.swing.JButton
 *   - javax.swing.JProgressBar
 *   - javax.swing.UIManager
 *   - javax.swing.plaf.ColorUIResource
 *   - java.awt.BorderLayout
 *   - java.awt.GridBagLayout
 *   - java.awt.GridBagConstraints
 *   - java.awt.Insets
 *   - java.awt.Component
 *
 * Usage:
 *   // Extend this frame for all pages:
 *   public class MyPage extends frame.TemplateFrame {
 *       public MyPage() {
 *           addMenuBarPanel();
 *           // build UI on centerPanel...
 *       }
 *   }
 *
 * TODO:
 *
 * =============================================================================
 */

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.UIManager;
import java.awt.*;

public class TemplateFrame extends JFrame {
    protected JPanel centerPanel; // gridBagLayout
    protected JPanel northPanel;  // BorderLayout
    protected GridBagConstraints c;

    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public TemplateFrame() {
        // Initialize panels
        centerPanel = new JPanel(new GridBagLayout());
        northPanel  = new JPanel(new BorderLayout());
        centerPanel.setBackground(Theme.BG_DARK);
        northPanel.setBackground(Theme.BG_DARK);

        // Window settings
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Global caret color for text inputs
        UIManager.put("TextField.caretForeground", new ColorUIResource(Theme.FG_LIGHT));
        UIManager.put("TextArea.caretForeground",  new ColorUIResource(Theme.FG_LIGHT));
        UIManager.put("PasswordField.caretForeground", new ColorUIResource(Theme.FG_LIGHT));

        // Frame layout
        setBackground(Theme.BG_DARK);
        setLayout(new BorderLayout());
        c = new GridBagConstraints();

        // Add the center panel
        add(centerPanel, BorderLayout.CENTER);
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void addMenuBarPanel() {
        JPanel menuBarPanel = new JPanel(new GridBagLayout());
        menuBarPanel.setBackground(Theme.BG_DARK);

        // user.User menu (left)
        c.insets = new Insets(0,0,0,0);
        c.gridx = 0; c.gridy = 0;
        c.weightx = 1.0; c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        UserMenu userMenu = new UserMenu();
        menuBarPanel.add(userMenu.addUserMenu(), c);

        // Logout menu (right)
        c.gridx = 1; c.gridy = 0;
        c.weightx = 0.0; c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        LogoutMenu logoutMenu = new LogoutMenu();
        menuBarPanel.add(logoutMenu.addLogoutMenu(), c);

        // Place into northPanel and add to frame
        northPanel.setBackground(Theme.BG_DARK);
        northPanel.add(menuBarPanel, BorderLayout.NORTH);
        add(northPanel, BorderLayout.NORTH);
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void addTextField(String prompt, JTextField textField, int x, int y) {
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Label
        c.gridx = x; c.gridy = y; c.anchor = GridBagConstraints.WEST;
        JLabel label = new JLabel(prompt);
        label.setForeground(Theme.FG_LIGHT);
        centerPanel.add(label, c);

        // Text field
        c.gridx = 1;
        textField.setBackground(Theme.BG_DARKER);
        textField.setForeground(Theme.FG_LIGHT);
        textField.setCaretColor(Theme.FG_LIGHT);
        centerPanel.add(textField, c);
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void addButton(JButton button, int row) {
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0; c.gridy = row; c.gridwidth = 2;

        button.setBackground(Theme.BUTTON_BG);
        button.setForeground(Theme.BUTTON_FG);
        centerPanel.add(button, c);
    }

    /**
     * Adds a themed progress bar (and label) into the northPanel.
     * If you pass an frame.AnimatedProgressBar, it will animate smoothly.
     */
    /**
     * * Description
     * *
     * * @param
     * * @return
     * * @throws
     */
    public JLabel addProgressBar(JProgressBar progressBar, int setValue, String progressText) {
        JPanel progressBarPanel = new JPanel();
        progressBarPanel.setLayout(new BoxLayout(progressBarPanel, BoxLayout.Y_AXIS));
        progressBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        progressBarPanel.setBackground(Theme.BG_DARK);

        // Configure the bar
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(400, 50));

        // Animate or set directly
        if (progressBar instanceof AnimatedProgressBar) {
            ((AnimatedProgressBar) progressBar).animateTo(setValue);
        } else {
            progressBar.setValue(setValue);
        }

        // Create the label
        JLabel progressLabel = new JLabel(progressText);
        progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressLabel.setForeground(Theme.FG_LIGHT);
        progressLabel.setBackground(Theme.BG_DARK);
        progressLabel.setOpaque(true);

        // Assemble
        progressBarPanel.add(progressBar);
        progressBarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        progressBarPanel.add(progressLabel);

        northPanel.add(progressBarPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        return progressLabel;
    }
}
