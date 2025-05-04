package frame;
/**
 * =============================================================================
 * File:        SessionContext.java
 * Author:      Dakota Hernandez
 * Created:     04/20/2025
 * -----------------------------------------------------------------------------
 * Description:
 *   A utility class that stores the currently selected date for the session.
 *   This allows different pages of the application to share and react to a
 *   consistent date context selected from the HomePage.
 *
 * Dependencies:
 *   - java.time.LocalDate
 *
 * Usage:
 *   // Set the session date when changed from the date picker
 *   SessionContext.setDate(LocalDate.now());
 *
 *   // Retrieve the date when opening a new page
 *   LocalDate date = SessionContext.getDate();
 * =============================================================================
 */

import java.time.LocalDate;

public class SessionContext {
    private static LocalDate selectedDate = LocalDate.now();

    /**
     * Retrieves the currently selected date stored in the session context.
     * This is typically used to synchronize the date across multiple pages.
     *
     * @return the currently selected LocalDate
     */

    public static LocalDate getDate() {
        return selectedDate;
    }

    /**
     * Updates the session's selected date to the specified value.
     * This allows other components to stay in sync with the chosen date.
     *
     * @param date the LocalDate to set as the current session date
     */
    public static void setDate(LocalDate date) {
        selectedDate = date;
    }
}
