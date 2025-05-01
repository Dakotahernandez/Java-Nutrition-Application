package tracking;

/**
 * =============================================================================
 * File:           tracking.FoodEntry.java
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
 *   tracking.FoodEntry entry = new tracking.FoodEntry(...);
 *
 * TODO:
 *   - Extend validation if needed.
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
     * Description
     *
     * @param
     * @return
     * @throws
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
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public String getFoodName() { return foodName; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public int getCalories() { return calories; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public String getProtein() { return protein; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public String getCarbs() { return carbs; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public String getFats() { return fats; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public String getFiber() { return fiber; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public String getNotes() { return notes; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public String getMealType() { return mealType; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void setFoodName(String foodName) { this.foodName = foodName; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void setCalories(int calories) { this.calories = calories; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void setProtein(String protein) { this.protein = protein; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void setCarbs(String carbs) { this.carbs = carbs; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void setFats(String fats) { this.fats = fats; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void setFiber(String fiber) { this.fiber = fiber; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void setNotes(String notes) { this.notes = notes; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void setMealType(String mealType) { this.mealType = mealType; }
}
