package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TrainerWorkoutPlan {
    public int id;
    public String title;
    public String description;
    public int trainerId;
    public int duration;
    public String level;
    public String category;

    public static void initializeTable() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:users.db");

        String sql = "CREATE TABLE IF NOT EXISTS Plans (" +
                "plan_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "description TEXT," +
                "trainer_id INTEGER NOT NULL," +
                "duration_days INTEGER NOT NULL," +
                "level TEXT CHECK(level IN ('Beginner', 'Intermediate', 'Advanced')) NOT NULL," +
                "category TEXT NOT NULL," +
                "FOREIGN KEY (trainer_id) REFERENCES Users(user_id)" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }




}
