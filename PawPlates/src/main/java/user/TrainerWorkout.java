package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TrainerWorkout {
    public int id;
    public int planId;
    public String name;
    public int day;

    public TrainerWorkout(int planId, String name, int day) {
        this.planId = planId;
        this.name = name;
        this.day = day;
    }

    public TrainerWorkout(int id, int planId, String name, int day) {
        this.id = id;
        this.planId = planId;
        this.name = name;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public int getDay() {
        return day;
    }

    public static void initializeTable() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:users.db");


        String sql = "CREATE TABLE IF NOT EXISTS Workouts (" +
                "workout_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "plan_id INTEGER NOT NULL," +
                "name TEXT NOT NULL," +
                "day INTEGER NOT NULL," +
                "FOREIGN KEY (plan_id) REFERENCES Plans(plan_id)" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE Workouts");
            stmt.execute(sql);
        }
    }


}
