/**
 * =============================================================================
 * File:        ExerciseTest.java
 * Authors:     Eli Hall
 * Created:     05/06/2025
 * -----------------------------------------------------------------------------
 * Description:
 *   Unit tests for the {@link Exercise} class, validating its constructors, getters,
 *   setters, and other methods such as equals, hashCode, and toString.
 *
 * Dependencies:
 *   - org.junit.jupiter.api.Test
 *   - tracking.Exercise
 *   - static org.junit.jupiter.api.Assertions.*
 *
 * Usage:
 *   Test the functionality of the Exercise class by running the test methods
 *   defined in this class using a test runner.
 *
 * =============================================================================
 */


import org.junit.jupiter.api.Test;
import tracking.Exercise;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Exercise} class, validating its constructors, getters, setters,
 * and other methods such as equals, hashCode, and toString.
 */
public class ExerciseTest {

    /**
     * Tests the default constructor of the {@link Exercise} class.
     * Verifies that all fields are initialized to their default values.
     */
    @Test
    public void testDefaultConstructor() {
        Exercise ex = new Exercise();
        assertEquals("", ex.getName());
        assertEquals("", ex.getFocus());
        assertEquals("", ex.getDescription());
        assertEquals(0, ex.getDuration());
        assertEquals(0, ex.getCaloriesBurned());
    }

    /**
     * Tests the constructor of the {@link Exercise} class with no ID.
     * Verifies that the provided values are correctly assigned and the ID is set to -1.
     */
    @Test
    public void testConstructorWithNoId() {
        Exercise ex = new Exercise("Push Up", "Chest", 20, 10, 100, "Upper body strength");
        assertEquals("Push Up", ex.getName());
        assertEquals("Chest", ex.getFocus());
        assertEquals(20, ex.getReps());
        assertEquals(10, ex.getDuration());
        assertEquals(100, ex.getCaloriesBurned());
        assertEquals("Upper body strength", ex.getDescription());
        assertEquals(-1, ex.getId()); // from constructor
    }

    /**
     * Tests the constructor of the {@link Exercise} class with a specified ID.
     * Verifies that the provided values, including the ID, are correctly assigned.
     */
    @Test
    public void testConstructorWithId() {
        Exercise ex = new Exercise(5, "Squat", "Legs", 30, 15, 150, "Leg exercise");
        assertEquals(5, ex.getId());
        assertEquals("Squat", ex.getName());
    }

    /**
     * Tests the setters and getters of the {@link Exercise} class.
     * Verifies that the setter methods correctly modify the fields and getter methods return the expected values.
     */
    @Test
    public void testSettersAndGetters() {
        Exercise ex = new Exercise();
        ex.setName("Jumping Jacks");
        ex.setFocus("Cardio");
        ex.setDescription("Warm-up");
        ex.setDuration(5);
        ex.setReps(50);
        ex.setCaloriesBurned(80);
        ex.setId(99);

        assertEquals("Jumping Jacks", ex.getName());
        assertEquals("Cardio", ex.getFocus());
        assertEquals("Warm-up", ex.getDescription());
        assertEquals(5, ex.getDuration());
        assertEquals(50, ex.getReps());
        assertEquals(80, ex.getCaloriesBurned());
        assertEquals(99, ex.getId());
    }

    /**
     * Tests the {@link Exercise#equals(Object)} and {@link Exercise#hashCode()} methods.
     * Verifies that two {@link Exercise} objects with the same data are considered equal
     * and have the same hash code.
     */
    @Test
    public void testEqualsAndHashCode() {
        Exercise ex1 = new Exercise("Sit Ups", "Abs", 25, 10, 90, "Core exercise");
        Exercise ex2 = new Exercise("Sit Ups", "Abs", 25, 10, 90, "Core exercise");

        assertEquals(ex1, ex2);
        assertEquals(ex1.hashCode(), ex2.hashCode());
    }

    /**
     * Tests the {@link Exercise#toString()} method.
     * Verifies that the string representation of the exercise matches the name of the exercise.
     */
    @Test
    public void testToString() {
        Exercise ex = new Exercise("Burpees", "Full Body", 10, 5, 120, "Explosive movement");
        assertEquals("Burpees", ex.toString());
    }
}
