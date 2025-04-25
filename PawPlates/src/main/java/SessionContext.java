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

    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public static LocalDate getDate() {
        return selectedDate;
    }

    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public static void setDate(LocalDate date) {
        selectedDate = date;
    }
}
