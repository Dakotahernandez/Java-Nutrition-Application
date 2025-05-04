package tracking.Food;

/**
 * =============================================================================
 * File:           tracking.Food.FoodEntry.java
 * Author:         Dakota Hernandez
 * Created:        04/20/25
 * -----------------------------------------------------------------------------
 * Description:
 *   A simple Java class representing a food entry. Contains fields for name,
 *   calories, macros, notes, and meal type, with getters and setters.
 *
 * Dependencies:
 *   - None (basic Java class)
 *
 * Usage:
 *   tracking.Food.FoodEntry entry = new tracking.Food.FoodEntry(...);
 *
 * =============================================================================
 */
public class FoodEntry {
    private String foodName;
    private int calories;
    private String protein;
    private String carbs;
    private String fats;
    private String fiber;
    private String notes;
    private String mealType;
    /**
     * Constructs a new FoodEntry with all nutritional and metadata fields.
     *
     * @param foodName  the name of the food
     * @param calories  the calorie count
     * @param protein   the amount of protein
     * @param carbs     the amount of carbohydrates
     * @param fats      the amount of fats
     * @param fiber     the amount of dietary fiber
     * @param notes     any additional notes
     * @param mealType  the meal this entry belongs to (e.g., Breakfast, Lunch, Dinner)
     */
    public FoodEntry(String foodName, int calories, String protein, String carbs, String fats, String fiber, String notes, String mealType) {
        this.foodName = foodName;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.fiber = fiber;
        this.notes = notes;
        this.mealType = mealType;
    }
    /**
     * Gets the name of the food.
     * @return the food name
     */
    public String getFoodName() { return foodName; }
    /**
     * Gets the number of calories.
     * @return the calorie count
     */
    public int getCalories() { return calories; }
    /**
     * Gets the amount of protein.
     * @return protein in grams or as a string
     */
    public String getProtein() { return protein; }
    /**
     * Gets the amount of carbohydrates.
     * @return carbs in grams or as a string
     */
    public String getCarbs() { return carbs; }
    /**
     * Gets the amount of fats.
     * @return fats in grams or as a string
     */
    public String getFats() { return fats; }
    /**
     * Gets the amount of dietary fiber.
     * @return fiber in grams or as a string
     */
    public String getFiber() { return fiber; }
    /**
     * Gets any additional notes.
     * @return notes for the food entry
     */
    public String getNotes() { return notes; }
    /**
     * Gets the meal type (Breakfast, Lunch, Dinner).
     * @return the meal type
     */
    public String getMealType() { return mealType; }
    /**
     * Sets the name of the food.
     * @param foodName the food name to set
     */
    public void setFoodName(String foodName) { this.foodName = foodName; }
    /**
     * Sets the calorie count.
     * @param calories the number of calories
     */
    public void setCalories(int calories) { this.calories = calories; }
    /**
     * Sets the amount of protein.
     * @param protein protein value to set
     */
    public void setProtein(String protein) { this.protein = protein; }
    /**
     * Sets the amount of carbohydrates.
     * @param carbs carbohydrate value to set
     */
    public void setCarbs(String carbs) { this.carbs = carbs; }
    /**
     * Sets the amount of fats.
     * @param fats fat value to set
     */
    public void setFats(String fats) { this.fats = fats; }
    /**
     * Sets the amount of dietary fiber.
     * @param fiber fiber value to set
     */
    public void setFiber(String fiber) { this.fiber = fiber; }
    /**
     * Sets additional notes.
     * @param notes notes to associate with this entry
     */
    public void setNotes(String notes) { this.notes = notes; }
    /**
     * Sets the meal type.
     * @param mealType the type of meal (Breakfast, Lunch, Dinner)
     */
    public void setMealType(String mealType) { this.mealType = mealType; }
}
