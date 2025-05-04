package frame; /**
 * =============================================================================
 * File:           frame.Theme.java
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
 *   // Reference frame.Theme.BG_DARK for background color
 *   panel.setBackground(frame.Theme.BG_DARK);
 *   // Use frame.Theme.NORMAL_FONT for standard text
 *   label.setFont(frame.Theme.NORMAL_FONT);
 *
 * TODO:
 *
 * =============================================================================
 */

import java.awt.Color;
import java.awt.Font;
import com.github.lgooddatepicker.components.DatePickerSettings;

/**
 * The frame.Theme class centralizes all the style settings for the application.
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
    public static final Color ACCENT_COLOR = new Color(10, 121, 107);
    public static final Color MID_GRAY = new Color(70, 70, 70);
    public static final Color BUTTON_BORDER = new Color(100, 100, 100);
    public static final Color GOAL_WEIGHT = new Color(255, 10, 10);
    // Button Styles
    public static final Color BUTTON_BG = MID_GRAY;
    public static final Color BUTTON_FG = FG_LIGHT;

    // Fonts
    public static final Font NORMAL_FONT = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font HEADER_FONT = new Font("SansSerif", Font.BOLD, 16);

    // ======================
    // Helper to apply dark mode to DatePickerSettings
    // ======================
    /**
     * Applies a cohesive dark theme to all visual elements of a DatePicker component
     * using the specified DatePickerSettings object. Adjusts background and text colors
     * to match the application's dark UI design.
     *
     * @param settings the DatePickerSettings instance to apply dark styling to
     */
    public static void applyDarkThemeToDatePicker(DatePickerSettings settings) {
        settings.setAllowEmptyDates(false);

        settings.setColor(DatePickerSettings.DateArea.BackgroundOverallCalendarPanel, BG_DARK);
        settings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels, BG_DARK);
        settings.setColor(DatePickerSettings.DateArea.TextMonthAndYearMenuLabels, FG_LIGHT);
        settings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearNavigationButtons, BG_DARK);
        settings.setColor(DatePickerSettings.DateArea.TextMonthAndYearNavigationButtons, FG_LIGHT);
        settings.setColor(DatePickerSettings.DateArea.BackgroundTopLeftLabelAboveWeekNumbers, BG_DARK);
        settings.setColor(DatePickerSettings.DateArea.BackgroundCalendarPanelLabelsOnHover, MID_GRAY);
        settings.setColor(DatePickerSettings.DateArea.CalendarTextWeekdays, FG_LIGHT);
        settings.setColor(DatePickerSettings.DateArea.CalendarTextWeekNumbers, FG_LIGHT);
        settings.setColor(DatePickerSettings.DateArea.CalendarTextNormalDates, FG_LIGHT);
        settings.setColor(DatePickerSettings.DateArea.CalendarBackgroundNormalDates, BG_DARKER);
        settings.setColor(DatePickerSettings.DateArea.CalendarBackgroundVetoedDates, BG_DARK);
        settings.setColor(DatePickerSettings.DateArea.CalendarBackgroundSelectedDate, ACCENT_COLOR);
        settings.setColor(DatePickerSettings.DateArea.DatePickerTextValidDate, FG_LIGHT);
        settings.setColor(DatePickerSettings.DateArea.BackgroundTodayLabel, BG_DARK);
        settings.setColor(DatePickerSettings.DateArea.TextTodayLabel, FG_LIGHT);
        settings.setColor(DatePickerSettings.DateArea.BackgroundClearLabel, BG_DARK);
        settings.setColor(DatePickerSettings.DateArea.TextClearLabel, FG_LIGHT);
    }
}