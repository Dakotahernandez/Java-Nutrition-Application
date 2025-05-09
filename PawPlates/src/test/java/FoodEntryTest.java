import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracking.Food.FoodEntry;

/**
 * Unit tests for the {@link FoodEntry} class.
 * Tests cover all getter and setter methods to ensure proper data encapsulation and retrieval.
 */
class FoodEntryTest {

    private FoodEntry foodEntry;

    /**
     * Initializes a default {@link FoodEntry} instance before each test.
     */
    @BeforeEach
    void setUp() {
        foodEntry = new FoodEntry("Apple", 95, "0.5g", "25g", "0.3g", "4.4g", "Fresh and healthy", "Snack");
    }

    /**
     * Tests the {@link FoodEntry#getFoodName()} method.
     */
    @Test
    void testGetFoodName() {
        assertEquals("Apple", foodEntry.getFoodName());
    }

    /**
     * Tests the {@link FoodEntry#getCalories()} method.
     */
    @Test
    void testGetCalories() {
        assertEquals(95, foodEntry.getCalories());
    }

    /**
     * Tests the {@link FoodEntry#getProtein()} method.
     */
    @Test
    void testGetProtein() {
        assertEquals("0.5g", foodEntry.getProtein());
    }

    /**
     * Tests the {@link FoodEntry#getCarbs()} method.
     */
    @Test
    void testGetCarbs() {
        assertEquals("25g", foodEntry.getCarbs());
    }

    /**
     * Tests the {@link FoodEntry#getFats()} method.
     */
    @Test
    void testGetFats() {
        assertEquals("0.3g", foodEntry.getFats());
    }

    /**
     * Tests the {@link FoodEntry#getFiber()} method.
     */
    @Test
    void testGetFiber() {
        assertEquals("4.4g", foodEntry.getFiber());
    }

    /**
     * Tests the {@link FoodEntry#getNotes()} method.
     */
    @Test
    void testGetNotes() {
        assertEquals("Fresh and healthy", foodEntry.getNotes());
    }

    /**
     * Tests the {@link FoodEntry#getMealType()} method.
     */
    @Test
    void testGetMealType() {
        assertEquals("Snack", foodEntry.getMealType());
    }

    /**
     * Tests the {@link FoodEntry#setFoodName(String)} method.
     */
    @Test
    void testSetFoodName() {
        foodEntry.setFoodName("Banana");
        assertEquals("Banana", foodEntry.getFoodName());
    }

    /**
     * Tests the {@link FoodEntry#setCalories(int)} method.
     */
    @Test
    void testSetCalories() {
        foodEntry.setCalories(105);
        assertEquals(105, foodEntry.getCalories());
    }

    /**
     * Tests the {@link FoodEntry#setProtein(String)} method.
     */
    @Test
    void testSetProtein() {
        foodEntry.setProtein("1.3g");
        assertEquals("1.3g", foodEntry.getProtein());
    }

    /**
     * Tests the {@link FoodEntry#setCarbs(String)} method.
     */
    @Test
    void testSetCarbs() {
        foodEntry.setCarbs("27g");
        assertEquals("27g", foodEntry.getCarbs());
    }

    /**
     * Tests the {@link FoodEntry#setFats(String)} method.
     */
    @Test
    void testSetFats() {
        foodEntry.setFats("0.4g");
        assertEquals("0.4g", foodEntry.getFats());
    }

    /**
     * Tests the {@link FoodEntry#setFiber(String)} method.
     */
    @Test
    void testSetFiber() {
        foodEntry.setFiber("5g");
        assertEquals("5g", foodEntry.getFiber());
    }

    /**
     * Tests the {@link FoodEntry#setNotes(String)} method.
     */
    @Test
    void testSetNotes() {
        foodEntry.setNotes("Tasty and filling");
        assertEquals("Tasty and filling", foodEntry.getNotes());
    }

    /**
     * Tests the {@link FoodEntry#setMealType(String)} method.
     */
    @Test
    void testSetMealType() {
        foodEntry.setMealType("Breakfast");
        assertEquals("Breakfast", foodEntry.getMealType());
    }
}
