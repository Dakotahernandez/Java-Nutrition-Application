import javax.swing.*;
import java.sql.*;

public class UserDatabase {
    private Connection connection;

    public UserDatabase() {
        initializeDatabase();
    }

    public boolean registerUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        try {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean loginUser(String username, String password) {
        boolean loginStatus = false;
        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                loginStatus = true;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return loginStatus;
    }

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
