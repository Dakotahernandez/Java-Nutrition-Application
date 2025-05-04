package tracking.weightAndGoals;
/**
 * =============================================================================
 * File:        WeightDatabase.java
 * Authors:     Dakota Hernandez
 * Created:     04/24/2025
 * -----------------------------------------------------------------------------
 * Description:
 *   Manages storage and retrieval of weight entries and comprehensive weight goals
 *   (including calorie and sleep targets) for each user in the same SQLite database
 *   as UserDatabase. Automatically migrates old schema to include new goal fields.
 *
 * Dependencies:
 *   - java.sql.Connection
 *   - java.sql.DriverManager
 *   - java.sql.PreparedStatement
 *   - java.sql.ResultSet
 *   - java.sql.SQLException
 *   - java.sql.Statement
 *   - java.time.LocalDate
 *   - java.util.Optional
 *   - java.util.List
 *   - java.util.ArrayList
 *
 * Usage:
 *   new WeightDatabase();
 *
 * =============================================================================
 */
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Manages storage and retrieval of weight entries and comprehensive weight goals
 * (including calorie and sleep targets) for each user in the same SQLite database
 * as UserDatabase. Automatically migrates old schema to include new goal fields.
 */
public class WeightDatabase {
    private static final String DB_URL = "jdbc:sqlite:users.db";
    private Connection conn;

    public WeightDatabase() {
        initialize();
    }
    /**
     * Initializes the SQLite connection and ensures required tables exist.
     * Also applies schema migrations for goal fields if needed.
     */
    private void initialize() {
        try {
            conn = DriverManager.getConnection(DB_URL);
            try (Statement stmt = conn.createStatement()) {
                // enable foreign keys
                stmt.execute("PRAGMA foreign_keys = ON");

                // migrate old weight_goals table if necessary
                try {
                    stmt.execute("ALTER TABLE weight_goals ADD COLUMN daily_cal_goal INTEGER NOT NULL DEFAULT 0");
                } catch (SQLException ignore) { }
                try {
                    stmt.execute("ALTER TABLE weight_goals ADD COLUMN weekly_sleep_goal INTEGER NOT NULL DEFAULT 0");
                } catch (SQLException ignore) { }

                // create entries table
                stmt.execute(
                        "CREATE TABLE IF NOT EXISTS weight_entries (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "user_id INTEGER NOT NULL, " +
                                "entry_date TEXT NOT NULL, " +
                                "weight REAL NOT NULL, " +
                                "FOREIGN KEY(user_id) REFERENCES users(id)" +
                                ")"
                );

                // create goals table
                stmt.execute(
                        "CREATE TABLE IF NOT EXISTS weight_goals (" +
                                "user_id INTEGER PRIMARY KEY, " +
                                "start_weight REAL NOT NULL, " +
                                "goal_weight REAL NOT NULL, " +
                                "daily_cal_goal INTEGER NOT NULL DEFAULT 0, " +
                                "weekly_sleep_goal INTEGER NOT NULL DEFAULT 0, " +
                                "FOREIGN KEY(user_id) REFERENCES users(id)" +
                                ")"
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize weight database", e);
        }
    }

    /**
     * Inserts or updates a user's weight goal information.
     *
     * @param userId           the user's ID
     * @param startWeight      the user's starting weight
     * @param goalWeight       the user's target weight
     * @param dailyCalGoal     the user's daily calorie goal
     * @param weeklySleepGoal  the user's weekly sleep goal in hours
     * @throws SQLException if a database error occurs
     */
    public void setWeightGoal(int userId,
                              double startWeight,
                              double goalWeight,
                              int dailyCalGoal,
                              int weeklySleepGoal) throws SQLException {
        String sql =
                "INSERT INTO weight_goals(user_id, start_weight, goal_weight, daily_cal_goal, weekly_sleep_goal) " +
                        "VALUES(?, ?, ?, ?, ?) " +
                        "ON CONFLICT(user_id) DO UPDATE SET " +
                        "start_weight      = excluded.start_weight, " +
                        "goal_weight       = excluded.goal_weight, " +
                        "daily_cal_goal    = excluded.daily_cal_goal, " +
                        "weekly_sleep_goal = excluded.weekly_sleep_goal";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setDouble(2, startWeight);
            ps.setDouble(3, goalWeight);
            ps.setInt(4, dailyCalGoal);
            ps.setInt(5, weeklySleepGoal);
            ps.executeUpdate();
        }
    }

    /**
     * Retrieves the weight goal for the given user if it exists.
     *
     * @param userId the user's ID
     * @return an Optional containing the WeightGoal if found, or empty otherwise
     * @throws SQLException if a database error occurs
     */
    public Optional<WeightGoal> getWeightGoal(int userId) throws SQLException {
        String sql =
                "SELECT start_weight, goal_weight, daily_cal_goal, weekly_sleep_goal " +
                        "FROM weight_goals WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new WeightGoal(
                            userId,
                            rs.getDouble("start_weight"),
                            rs.getDouble("goal_weight"),
                            rs.getInt("daily_cal_goal"),
                            rs.getInt("weekly_sleep_goal")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Inserts or updates a weight entry for the user on a specific date.
     *
     * @param userId the user's ID
     * @param date   the date of the weight entry
     * @param weight the user's weight on that date
     * @throws SQLException if a database error occurs
     */
    public void addWeightEntry(int userId, LocalDate date, double weight) throws SQLException {
        String sql =
                "INSERT OR REPLACE INTO weight_entries(user_id, entry_date, weight) " +
                        "VALUES(?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, date.toString());
            ps.setDouble(3, weight);
            ps.executeUpdate();
        }
    }

    /**
     * Retrieves all weight entries for the given user, ordered by date.
     *
     * @param userId the user's ID
     * @return a list of WeightEntry objects sorted by date ascending
     * @throws SQLException if a database error occurs
     */
    public List<WeightEntry> getWeightEntries(int userId) throws SQLException {
        String sql =
                "SELECT entry_date, weight FROM weight_entries " +
                        "WHERE user_id = ? ORDER BY entry_date ASC";
        List<WeightEntry> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LocalDate d  = LocalDate.parse(rs.getString("entry_date"));
                    double w     = rs.getDouble("weight");
                    list.add(new WeightEntry(userId, d, w));
                }
            }
        }
        return list;
    }

    // --- DTO Classes ---

    /**
     * Represents a single weight entry with date and weight value.
     */
    public static class WeightGoal {
        public final int userId;
        public final double startWeight, goalWeight;
        public final int dailyCalGoal, weeklySleepGoal;
        public WeightGoal(int userId,
                          double startWeight,
                          double goalWeight,
                          int dailyCalGoal,
                          int weeklySleepGoal) {
            this.userId          = userId;
            this.startWeight     = startWeight;
            this.goalWeight      = goalWeight;
            this.dailyCalGoal    = dailyCalGoal;
            this.weeklySleepGoal = weeklySleepGoal;
        }
    }

    /**
     * Simple holder for one weight reading.
     */
    public static class WeightEntry {
        public final int userId;
        public final LocalDate date;
        public final double weight;
        public WeightEntry(int userId, LocalDate date, double weight) {
            this.userId = userId;
            this.date   = date;
            this.weight = weight;
        }
    }
}

