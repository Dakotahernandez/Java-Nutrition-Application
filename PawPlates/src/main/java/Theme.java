/**
 * =============================================================================
 * File:           Theme.java
 * Author:         Dakota Hernandez
 * Created:        04/20/25
 * -----------------------------------------------------------------------------
 * Description:
 *   Centralizes the application's color palette and font settings for
 *   consistent theming across all UI components.
 *
 * Dependencies:
 *   - java.awt.Color
 *   - java.awt.Font
 *
 * Usage:
 *   // Reference Theme.BG_DARK for background color
 *   panel.setBackground(Theme.BG_DARK);
 *   // Use Theme.NORMAL_FONT for standard text
 *   label.setFont(Theme.NORMAL_FONT);
 *
 * TODO:
 *
 * =============================================================================
 */

import java.awt.Color;
import java.awt.Font;

/**
 * The Theme class centralizes all the style settings for the application.
 * Modify these constants to update the color scheme and fonts project-wide.
 */
public final class Theme {
    private Theme() {}

    // Background Colors
    public static final Color BG_DARK = new Color(40, 40, 40);
    public static final Color BG_DARKER = new Color(30, 30, 30);
    public static final Color BG_LIGHTER = new Color(60, 60, 60); // Used for highlights

    // Foreground Colors
    public static final Color FG_LIGHT = new Color(220, 220, 220);
    public static final Color FG_MUTED = new Color(180, 180, 180);

    // Accent + Borders
    public static final Color ACCENT_GREEN = new Color(75, 199, 170);
    public static final Color MID_GRAY = new Color(70, 70, 70);
    public static final Color BUTTON_BORDER = new Color(100, 100, 100);

    // Button Styles
    public static final Color BUTTON_BG = MID_GRAY;
    public static final Color BUTTON_FG = FG_LIGHT;

    // Fonts
    public static final Font NORMAL_FONT = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font HEADER_FONT = new Font("SansSerif", Font.BOLD, 16);
}
