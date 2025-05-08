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
import tracking.UserPlanDetails;

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
            String sql = "INSERT INTO users (username, password, email, isTrainer) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setBoolean(4, user.isTrainer());
            ps.executeUpdate();
            System.out.println("Account created for user " + user.getUsername());

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getInt(1));
                    System.out.println("User associated with id " + user.getId());
                }
                else {
                    System.out.println("No id generated");
                }
            }

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
    public User loginUser(String username, String password) throws IllegalArgumentException {
        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet result = ps.executeQuery()) {
                if (result.next()) {
                    User user = new User(
                            result.getString("username"),
                            result.getString("password"));
                    user.setEmail(result.getString("email"));
                    user.setTrainer(result.getBoolean("isTrainer"));
                    user.setId(result.getInt("id"));

                    System.out.println("Login successful for user " + user.getUsername());
                    return user;
                }
                else {
                    throw new IllegalArgumentException("Invalid username or password. Try again.");
                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            return null;
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
                    "password TEXT NOT NULL," +
                    "email TEXT NOT NULL UNIQUE," +
                    "isTrainer BOOLEAN NOT NULL DEFAULT 0" +
                    ")";

            Statement statement = connection.createStatement();
            statement.execute(sql);

            TrainerWorkoutPlan.initializeTable();
            TrainerWorkout.initializeTable();
            TrainerExercise.initializeTable();

            UserPlanDetails.initializeTable();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void dropTable() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
            String sql = "DROP TABLE users";
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public User getUserById(int id) {
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                User user = new User(
                        result.getString("username"),
                        result.getString("password")
                );
                user.setId(id);
                user.setEmail(result.getString("email"));
                user.setTrainer(result.getBoolean("isTrainer"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

