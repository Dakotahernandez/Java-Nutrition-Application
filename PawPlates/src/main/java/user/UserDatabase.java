package user; /**
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
import java.sql.*;

public class UserDatabase {
    private Connection connection;
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public UserDatabase() {
        initializeDatabase();
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void registerUser(User user) throws IllegalArgumentException {
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Username or password cannot be empty.");
        }

        if (usernameExists(user.getUsername())) {
            throw new IllegalArgumentException("A user with this username already exists.");
        }

        try {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void loginUser(User user) throws IllegalArgumentException {
        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            ResultSet result = ps.executeQuery();

            if (!result.next()) {
                throw new IllegalArgumentException("Invalid username or password. Try again.");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public boolean usernameExists(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");

            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL)";

            Statement statement = connection.createStatement();
            statement.execute(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
