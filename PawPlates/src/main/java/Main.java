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
import com.formdev.flatlaf.FlatDarkLaf;

public class Main {
    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup(); // Global dark mode
        } catch (Exception e) {
            e.printStackTrace();
        }

        new LoginPage(); // Start the app
    }
}
