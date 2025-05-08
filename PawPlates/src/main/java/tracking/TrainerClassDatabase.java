package tracking;

import frame.LoginPage;
import user.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainerClassDatabase {
    private static Connection connection;

    public TrainerClassDatabase() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
            Statement stmt = connection.createStatement();

            // Main table for trainer classes
            String trainerTable = "CREATE TABLE IF NOT EXISTS trainer_class (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "date TEXT NOT NULL," +
                    "trainer_id INTEGER NOT NULL" +
                    ")";
            stmt.executeUpdate(trainerTable);

            // Join table linking classes to users
            String userJoin = "CREATE TABLE IF NOT EXISTS trainer_class_user (" +
                    "trainer_class_id INTEGER NOT NULL," +
                    "user_id INTEGER NOT NULL," +
                    "FOREIGN KEY(trainer_class_id) REFERENCES trainer_class(id) ON DELETE CASCADE" +
                    ")";
            stmt.executeUpdate(userJoin);

            // Join table linking classes to exercises
            String exerciseJoin = "CREATE TABLE IF NOT EXISTS trainer_class_exercise (" +
                    "trainer_class_id INTEGER NOT NULL," +
                    "exercise_id INTEGER NOT NULL," +
                    "FOREIGN KEY(trainer_class_id) REFERENCES trainer_class(id) ON DELETE CASCADE," +
                    "FOREIGN KEY(exercise_id) REFERENCES exercise(id) ON DELETE CASCADE" +
                    ")";
            stmt.executeUpdate(exerciseJoin);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int saveTrainerClass(TrainerClass trainerClass) {
        try {
            String sql = "INSERT INTO trainer_class (name, date, trainer_id) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, trainerClass.getName());
            ps.setString(2, trainerClass.getDate().toString());
            ps.setInt(3, trainerClass.getTrainerId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int classId = rs.getInt(1);

                String userLink = "INSERT INTO trainer_class_user (trainer_class_id, user_id) VALUES (?, ?)";
                try (PreparedStatement ups = connection.prepareStatement(userLink)) {
                    for (int u : trainerClass.getUserIds()) {
                        ups.setInt(1, classId);
                        ups.setInt(2, u);
                        ups.executeUpdate();
                    }
                }

                String exLink = "INSERT INTO trainer_class_exercise (trainer_class_id, exercise_id) VALUES (?, ?)";
                try (PreparedStatement exps = connection.prepareStatement(exLink)) {
                    for (Exercise e : trainerClass.getExercises()) {
                        exps.setInt(1, classId);
                        exps.setInt(2, e.getId());
                        exps.executeUpdate();
                    }
                }

                return classId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<TrainerClass> loadTrainerClasses() {
        List<TrainerClass> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM trainer_class";
            try (PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id        = rs.getInt("id");
                    String name   = rs.getString("name");
                    LocalDate dt  = LocalDate.parse(rs.getString("date"));
                    int trainerId = rs.getInt("trainer_id");

                    List<Integer> users     = loadUserIds(id);
                    List<Exercise> exercises = loadExercises(id);

                    list.add(new TrainerClass(id, dt, name, exercises, trainerId, users));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<Integer> loadUserIds(int classId) {
        List<Integer> users = new ArrayList<>();
        try {
            String sql = "SELECT user_id FROM trainer_class_user WHERE trainer_class_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, classId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        users.add(rs.getInt("user_id"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private List<Exercise> loadExercises(int classId) {
        List<Exercise> exList = new ArrayList<>();
        try {
            String sql = "SELECT e.* FROM exercise e " +
                    "JOIN trainer_class_exercise tce ON e.id = tce.exercise_id " +
                    "WHERE tce.trainer_class_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, classId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        exList.add(new Exercise(
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exList;
    }

    public void deleteTrainerClass(int id) {
        try {
            String sql = "DELETE FROM trainer_class WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TrainerClass> loadClassesForUser(int userId) {
        List<TrainerClass> classes = new ArrayList<>();

        String sql = "SELECT tc.* FROM trainer_class tc " +
                "JOIN trainer_class_user tcu ON tc.id = tcu.trainer_class_id " +
                "WHERE tcu.user_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    LocalDate date = LocalDate.parse(rs.getString("date"));
                    int trainerId = rs.getInt("trainer_id");

                    List<Integer> users = loadUserIds(id);
                    List<Exercise> exercises = loadExercises(id);
                    classes.add(new TrainerClass(id, date, name, exercises, trainerId, users));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classes;
    }

    public boolean updateTrainerClass(TrainerClass trainerClass) {
        try {
            String sql = "UPDATE trainer_class SET name = ?, date = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, trainerClass.getName());
            ps.setString(2, trainerClass.getDate().toString());
            ps.setInt(3, trainerClass.getId());
            ps.executeUpdate();

            String delUsers = "DELETE FROM trainer_class_user WHERE trainer_class_id = ?";
            PreparedStatement delUserPs = connection.prepareStatement(delUsers);
            delUserPs.setInt(1, trainerClass.getId());
            delUserPs.executeUpdate();

            String insUser = "INSERT INTO trainer_class_user (trainer_class_id, user_id) VALUES (?, ?)";
            PreparedStatement insUserPs = connection.prepareStatement(insUser);
            for (int userId : trainerClass.getUserIds()) {
                insUserPs.setInt(1, trainerClass.getId());
                insUserPs.setInt(2, userId);
                insUserPs.executeUpdate();
            }

            String delEx = "DELETE FROM trainer_class_exercise WHERE trainer_class_id = ?";
            PreparedStatement delExPs = connection.prepareStatement(delEx);
            delExPs.setInt(1, trainerClass.getId());
            delExPs.executeUpdate();

            String insEx = "INSERT INTO trainer_class_exercise (trainer_class_id, exercise_id) VALUES (?, ?)";
            PreparedStatement insExPs = connection.prepareStatement(insEx);
            for (Exercise ex : trainerClass.getExercises()) {
                insExPs.setInt(1, trainerClass.getId());
                insExPs.setInt(2, ex.getId());
                insExPs.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateUserList(TrainerClass trainerClass) {
        try {
            String deleteSQL = "DELETE FROM trainer_class_user WHERE trainer_class_id = ?";
            PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL);
            deleteStmt.setInt(1, trainerClass.getId());
            deleteStmt.executeUpdate();

            String insertSQL = "INSERT INTO trainer_class_user (trainer_class_id, user_id) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertSQL);
            for (int uid : trainerClass.getUserIds()) {
                insertStmt.setInt(1, trainerClass.getId());
                insertStmt.setInt(2, uid);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}