package tracking.Food;
/**
 * =============================================================================
 * File:        FoodEntryDatabase.java
 * Authors:     Dakota Hernandez
 * Created:     04/24/2025
 * -----------------------------------------------------------------------------
 * Description:
 *   Manages storage and CRUD operations for food entries per user and date.
 *   Integrates with CalorieMacroPage.FoodEntry and persists entries in SQLite.
 *
 * Dependencies:
 *   - user.User
 *   - frame.LoginPage
 *   - java.sql.Connection
 *   - java.sql.DriverManager
 *   - java.sql.PreparedStatement
 *   - java.sql.ResultSet
 *   - java.sql.SQLException
 *   - java.sql.Statement
 *   - java.time.LocalDate
 *   - java.util.List
 *   - java.util.ArrayList
 *   - tracking.Food.CalorieMacroPage.FoodEntry
 *
 * Usage:
 *   new FoodEntryDatabase();
 *
 * =============================================================================
 */
import user.User;
import frame.LoginPage;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Handles database operations related to food entries.
 * Provides methods for initializing the table, saving, loading, updating, and deleting entries.
 */
public class FoodEntryDatabase {
    private static Connection connection;
    /**
     * Establishes the database connection and ensures the food_entries table exists.
     */
    public FoodEntryDatabase() {
        initializeDatabase();
    }
    /**
     * Initializes the food_entries table in the database if it doesn't already exist.
     */
    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
            String sql = "CREATE TABLE IF NOT EXISTS food_entries (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "entry_date TEXT NOT NULL," +
                    "meal_type TEXT NOT NULL," +
                    "food_name TEXT NOT NULL," +
                    "calories INTEGER NOT NULL," +
                    "protein TEXT," +
                    "carbs TEXT," +
                    "fats TEXT," +
                    "fiber TEXT," +
                    "notes TEXT," +
                    "FOREIGN KEY(user_id) REFERENCES users(id))";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Saves a new food entry to the database for the current user on the specified date.
     *
     * @param entry the food entry to save
     * @param date the date the entry is associated with
     */
    public void saveEntry(CalorieMacroPage.FoodEntry entry, LocalDate date) {
        try {
            String sql = "INSERT INTO food_entries (user_id, entry_date, meal_type, food_name, calories, protein, carbs, fats, fiber, notes) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            User user = LoginPage.CURRENT_USER;

            ps.setInt(1, user.getId());
            ps.setString(2, date.toString());
            ps.setString(3, entry.getMealType());
            ps.setString(4, entry.getFoodName());
            ps.setInt(5, entry.getCalories());
            ps.setString(6, entry.getProtein());
            ps.setString(7, entry.getCarbs());
            ps.setString(8, entry.getFats());
            ps.setString(9, entry.getFiber());
            ps.setString(10, entry.getNotes());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Loads all food entries for a specific user, date, and meal type.
     *
     * @param userId the user ID
     * @param date the date to query
     * @param mealType the meal type (e.g., "Breakfast", "Lunch", "Dinner")
     * @return a list of matching FoodEntry objects
     */
    public List<CalorieMacroPage.FoodEntry> loadEntries(int userId, LocalDate date, String mealType) {
        List<CalorieMacroPage.FoodEntry> entries = new ArrayList<>();
        try {
            String sql = "SELECT * FROM food_entries WHERE user_id = ? AND entry_date = ? AND meal_type = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, date.toString());
            ps.setString(3, mealType);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                entries.add(new CalorieMacroPage.FoodEntry(
                        rs.getString("food_name"),
                        rs.getInt("calories"),
                        rs.getString("protein"),
                        rs.getString("carbs"),
                        rs.getString("fats"),
                        rs.getString("fiber"),
                        rs.getString("notes"),
                        rs.getString("meal_type")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }
    /**
     * Deletes a specific food entry for the current user based on date, meal type, and food name.
     *
     * @param entry the food entry to delete
     * @param date the date the entry was recorded
     */
    public static void deleteEntry(CalorieMacroPage.FoodEntry entry, LocalDate date) {
        try {
            String sql = "DELETE FROM food_entries WHERE user_id = ? AND entry_date = ? AND meal_type = ? AND food_name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            User user = LoginPage.CURRENT_USER;

            ps.setInt(1, user.getId());
            ps.setString(2, date.toString());
            ps.setString(3, entry.getMealType());
            ps.setString(4, entry.getFoodName());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Updates a food entry in the database with new values.
     * Identifies the row by old entry's food name and meal type for the current user and date.
     *
     * @param oldEntry the original entry to be updated
     * @param newEntry the new entry data to save
     * @param date the date the entry is associated with
     */
    public static void updateEntry(CalorieMacroPage.FoodEntry oldEntry, CalorieMacroPage.FoodEntry newEntry, LocalDate date) {
        try {
            String sql = "UPDATE food_entries SET " +
                    "food_name = ?, calories = ?, protein = ?, carbs = ?, fats = ?, fiber = ?, notes = ?, meal_type = ? " +
                    "WHERE user_id = ? AND entry_date = ? AND meal_type = ? AND food_name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            User user = LoginPage.CURRENT_USER;

            ps.setString(1, newEntry.getFoodName());
            ps.setInt(2, newEntry.getCalories());
            ps.setString(3, newEntry.getProtein());
            ps.setString(4, newEntry.getCarbs());
            ps.setString(5, newEntry.getFats());
            ps.setString(6, newEntry.getFiber());
            ps.setString(7, newEntry.getNotes());
            ps.setString(8, newEntry.getMealType());

            ps.setInt(9, user.getId());
            ps.setString(10, date.toString());
            ps.setString(11, oldEntry.getMealType());
            ps.setString(12, oldEntry.getFoodName());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Deletes all food entries for a specific user on a given date.
     *
     * @param userId the user ID
     * @param date the date for which to delete entries
     */
    public void deleteAllEntriesForUserOnDate(int userId, LocalDate date) {
        try {
            String sql = "DELETE FROM food_entries WHERE user_id = ? AND entry_date = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, date.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
