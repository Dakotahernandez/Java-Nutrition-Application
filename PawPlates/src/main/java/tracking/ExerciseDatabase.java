package tracking;
import user.User;
import frame.LoginPage;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDatabase {
    private static Connection connection;

    public ExerciseDatabase() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
            String sql = "CREATE TABLE IF NOT EXISTS exercise (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "name TEXT NOT NULL," +
                    "focus TEXT," +
                    "description TEXT," +
                    "duration INTEGER," +
                    "reps INTEGER," +
                    "calories_burned INTEGER," +
                    "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE)";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int saveExercise(Exercise exercise) {
        try {
            String sql = "INSERT INTO exercise (user_id, name, focus, description, duration, reps, calories_burned) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            User user = LoginPage.CURRENT_USER;

            ps.setInt(1, user.getId());
            ps.setString(2, exercise.getName());
            ps.setString(3, exercise.getFocus());
            ps.setString(4, exercise.getDescription());
            ps.setInt(5, exercise.getDuration());
            ps.setInt(6, exercise.getReps());
            ps.setInt(7, exercise.getCaloriesBurned());

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
            if(rs.next()) {
                return rs.getInt(1);
            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Exercise> loadExercisesForUser(int userId) {
        List<Exercise> exercises = new ArrayList<>();
        try {
            String sql = "SELECT * FROM exercise WHERE user_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    public void deleteExercise(int exerciseId) {
        try {
            String sql = "DELETE FROM exercise WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, exerciseId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
