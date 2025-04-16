import java.awt.Color;
import java.awt.Font;

/**
 * The Theme class centralizes all the style settings for the application.
 * Modify these constants to update the color scheme and fonts project-wide.
 */
public final class Theme {
    // Prevent instantiation.
    private Theme() {}

    // Background Colors.
    public static final Color BG_DARK = new Color(40, 40, 40);       // Primary dark background.
    public static final Color BG_DARKER = new Color(30, 30, 30);     // For tables, dialogs, etc.

    // Foreground Colors.
    public static final Color FG_LIGHT = new Color(220, 220, 220);   // Light gray text.

    // Accent Colors.
    public static final Color ACCENT_GREEN = new Color(0, 200, 83);  // Used for progress bars, etc.
    public static final Color MID_GRAY = new Color(70, 70, 70);       // Middle gray for borders and other elements.

    // Button Style.
    public static final Color BUTTON_BG = MID_GRAY;    // Grey background for buttons.
    public static final Color BUTTON_FG = Color.BLACK;   // Updated: Black text for buttons.

    // Fonts.
    public static final Font NORMAL_FONT = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font HEADER_FONT = new Font("SansSerif", Font.BOLD, 16);
}
