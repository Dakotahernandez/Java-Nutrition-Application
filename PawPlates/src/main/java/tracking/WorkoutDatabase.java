package tracking;

import frame.LoginPage;
import user.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDatabase {
    private static Connection connection;

    public WorkoutDatabase() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
            String workoutTable = "CREATE TABLE IF NOT EXISTS workout (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "name TEXT NOT NULL," +
                    "date TEXT NOT NULL," +
                    "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE)";
            String joinTable = "CREATE TABLE IF NOT EXISTS workout_exercise (" +
                    "workout_id INTEGER NOT NULL," +
                    "exercise_id INTEGER NOT NULL," +
                    "FOREIGN KEY(workout_id) REFERENCES workout(id) ON DELETE CASCADE," +
                    "FOREIGN KEY(exercise_id) REFERENCES exercise(id) ON DELETE CASCADE)";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(workoutTable);
            stmt.executeUpdate(joinTable);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int saveWorkout(Workout workout) {
        String sql = "INSERT INTO workout (user_id, name, date) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            User user = LoginPage.CURRENT_USER;
            ps.setInt(1, user.getId());
            ps.setString(2, workout.getName());
            ps.setString(3, workout.getDate().toString());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int workoutId = rs.getInt(1);
                    for(Exercise e: workout.getExercises()) {
                        String sql1 = "INSERT INTO workout_exercise (workout_id, exercise_id) VALUES (?, ?)";
                        try (PreparedStatement ps1 = connection.prepareStatement(sql1)) {
                            ps1.setInt(1, workoutId);
                            ps1.setInt(2, e.getId());
                            ps1.executeUpdate();
                        }
                    }
                    return workoutId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Workout> loadWorkoutsForUser(int userId) {
        List<Workout> workouts = new ArrayList<>();
        String sql = "SELECT * FROM workout WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int workoutID = rs.getInt("id");
                    LocalDate date = LocalDate.parse(rs.getString("date"));
                    String name = rs.getString("name");

                    List<Exercise> exercises = loadExercisesForWorkout(workoutID);
                    workouts.add(new Workout(workoutID, date, name, exercises));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workouts;
    }

    public void deleteWorkout(int workoutId) {
        String sql = "DELETE FROM workout WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, workoutId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Exercise> loadExercisesForWorkout(int workoutId) {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT exercise.* " +
                "FROM exercise " +
                "JOIN workout_exercise ON exercise.id = workout_exercise.exercise_id " +
                "WHERE workout_exercise.workout_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, workoutId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    exercises.add(new Exercise(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("focus"),
                            rs.getInt("reps"),
                            rs.getInt("duration"),
                            rs.getInt("calories_burned"),
                            rs.getString("description")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exercises;
    }

}
