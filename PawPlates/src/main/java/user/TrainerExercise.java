package user;
/**
 * =============================================================================
 * File:           user.TrainerExercise.java
 * Authors:        Mac Johnson
 * Created:        05/08/25
 * -----------------------------------------------------------------------------
 * Description:
 *   Represents an exercise assigned by a trainer, including name, sets, and reps.
 *   Also provides static initialization method to create the Exercises table
 *   in the SQLite database, linked to Workouts via a foreign key.
 *
 * Dependencies:
 *   - java.sql.Connection
 *   - java.sql.DriverManager
 *   - java.sql.SQLException
 *   - java.sql.Statement
 *
 * Usage:
 *   // Create a TrainerExercise instance
 *   TrainerExercise exercise = new TrainerExercise(workoutId, "Push Ups", 3, 15);
 *
 *   // Initialize database table
 *   TrainerExercise.initializeTable();
 *
 * TODO:
 *   - Add database insert and query methods for TrainerExercise records
 * =============================================================================
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TrainerExercise {
    int id;
    int workoutId;
    String name;
    int sets;
    int reps;

    public TrainerExercise(int workoutId, String name, int sets, int reps) {
        this.workoutId = workoutId;
        this.name = name;
        this.sets = sets;
        this.reps = reps;
    }

    public TrainerExercise(int id, int workoutId, String name, int sets, int reps) {
        this.id = id;
        this.workoutId = workoutId;
        this.name = name;
        this.sets = sets;
        this.reps = reps;
    }

    public String getName() {
        return name;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public static void initializeTable() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:users.db");

        String sql = "CREATE TABLE IF NOT EXISTS Exercises (" +
                "exercise_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "workout_id INTEGER NOT NULL," +
                "name TEXT NOT NULL," +
                "sets INTEGER NOT NULL," +
                "reps INTEGER NOT NULL," +
                "FOREIGN KEY (workout_id) REFERENCES Workouts(workout_id)" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }
}
