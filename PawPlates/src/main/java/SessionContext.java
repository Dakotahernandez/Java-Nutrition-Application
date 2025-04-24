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
import java.time.LocalDate;

public class SessionContext {
    private static LocalDate selectedDate = LocalDate.now();

    public static LocalDate getDate() {
        return selectedDate;
    }

    public static void setDate(LocalDate date) {
        selectedDate = date;
    }
}
