import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracking.Food.FoodEntry;

class FoodEntryTest {

    private FoodEntry foodEntry;

    @BeforeEach
    void setUp() {
        foodEntry = new FoodEntry("Apple", 95, "0.5g", "25g", "0.3g", "4.4g", "Fresh and healthy", "Snack");
    }

    @Test
    void testGetFoodName() {
        assertEquals("Apple", foodEntry.getFoodName());
    }

    @Test
    void testGetCalories() {
        assertEquals(95, foodEntry.getCalories());
    }

    @Test
    void testGetProtein() {
        assertEquals("0.5g", foodEntry.getProtein());
    }

    @Test
    void testGetCarbs() {
        assertEquals("25g", foodEntry.getCarbs());
    }

    @Test
    void testGetFats() {
        assertEquals("0.3g", foodEntry.getFats());
    }

    @Test
    void testGetFiber() {
        assertEquals("4.4g", foodEntry.getFiber());
    }

    @Test
    void testGetNotes() {
        assertEquals("Fresh and healthy", foodEntry.getNotes());
    }

    @Test
    void testGetMealType() {
        assertEquals("Snack", foodEntry.getMealType());
    }

    @Test
    void testSetFoodName() {
        foodEntry.setFoodName("Banana");
        assertEquals("Banana", foodEntry.getFoodName());
    }

    @Test
    void testSetCalories() {
        foodEntry.setCalories(105);
        assertEquals(105, foodEntry.getCalories());
    }

    @Test
    void testSetProtein() {
        foodEntry.setProtein("1.3g");
        assertEquals("1.3g", foodEntry.getProtein());
    }

    @Test
    void testSetCarbs() {
        foodEntry.setCarbs("27g");
        assertEquals("27g", foodEntry.getCarbs());
    }

    @Test
    void testSetFats() {
        foodEntry.setFats("0.4g");
        assertEquals("0.4g", foodEntry.getFats());
    }

    @Test
    void testSetFiber() {
        foodEntry.setFiber("5g");
        assertEquals("5g", foodEntry.getFiber());
    }

    @Test
    void testSetNotes() {
        foodEntry.setNotes("Tasty and filling");
        assertEquals("Tasty and filling", foodEntry.getNotes());
    }

    @Test
    void testSetMealType() {
        foodEntry.setMealType("Breakfast");
        assertEquals("Breakfast", foodEntry.getMealType());
    }
}
