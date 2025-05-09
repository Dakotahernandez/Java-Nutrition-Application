/**
 * =============================================================================
 * File:        TrainerClassTest.java
 * Authors:     Eli Hall
 * Created:     05/08/2025
 * -----------------------------------------------------------------------------
 * Description:
 *   Unit tests for the TrainerClass class. These tests verify the correct
 *   functionality of the TrainerClass constructor, methods for adding/removing
 *   users, and the functionality of the class with various inputs.
 *
 * Dependencies:
 *   - org.junit.jupiter.api.*
 *   - tracking.Exercise
 *   - tracking.TrainerClass
 *
 * Usage:
 *   Test the functionality of TrainerClass by running the test methods
 *   defined in this class using a test runner.
 *
 * =============================================================================
 */


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracking.Exercise;
import tracking.TrainerClass;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link TrainerClass} class.
 * Verifies correct behavior of constructors, user ID management,
 * and getter/setter methods.
 */
public class TrainerClassTest {

    private Exercise exercise1;
    private Exercise exercise2;
    private List<Exercise> exercises;
    private List<Integer> userIds;

    /**
     * Initializes sample {@link Exercise} instances and user ID lists before each test.
     */
    @BeforeEach
    public void setUp() {
        exercise1 = new Exercise("Squats", "Strength", 15, 10, 60, "Basic squats");
        exercise2 = new Exercise("Cycling", "Cardio", 0, 20, 200, "Stationary cycling");

        exercises = new ArrayList<>(Arrays.asList(exercise1, exercise2));
        userIds = new ArrayList<>(Arrays.asList(101, 102));
    }

    /**
     * Tests the {@link TrainerClass} constructor that includes an ID.
     * Verifies that all fields are properly set and user list is populated.
     */
    @Test
    public void testConstructorWithId() {
        TrainerClass trainerClass = new TrainerClass(1, LocalDate.of(2025, 5, 8), "Bootcamp", exercises, 10, userIds);

        assertEquals(1, trainerClass.getId());
        assertEquals("Bootcamp", trainerClass.getName());
        assertEquals(10, trainerClass.getTrainerId());
        assertEquals(2, trainerClass.getUserIds().size());
    }

    /**
     * Tests the {@link TrainerClass} constructor without an ID.
     * Confirms that other fields are correctly initialized.
     */
    @Test
    public void testConstructorWithoutId() {
        TrainerClass trainerClass = new TrainerClass(LocalDate.of(2025, 5, 8), "HIIT", exercises, 20, userIds);

        assertEquals("HIIT", trainerClass.getName());
        assertEquals(20, trainerClass.getTrainerId());
        assertEquals(2, trainerClass.getUserIds().size());
    }

    /**
     * Tests the {@link TrainerClass#addUserId(int)} method.
     * Verifies that user IDs are added correctly and duplicates are prevented.
     */
    @Test
    public void testAddUserId() {
        TrainerClass trainerClass = new TrainerClass(LocalDate.now(), "Yoga", exercises, 5, new ArrayList<>());

        assertTrue(trainerClass.addUserId(200));
        assertFalse(trainerClass.addUserId(200)); // no duplicates
        assertEquals(1, trainerClass.getUserIds().size());
    }

    /**
     * Tests the {@link TrainerClass#removeUserId(int)} method.
     * Ensures that user IDs can be removed and handles attempts to remove non-existent IDs.
     */
    @Test
    public void testRemoveUserId() {
        TrainerClass trainerClass = new TrainerClass(LocalDate.now(), "Zumba", exercises, 5, new ArrayList<>(List.of(300, 301)));

        assertTrue(trainerClass.removeUserId(300));
        assertFalse(trainerClass.removeUserId(400)); // not in list
        assertEquals(1, trainerClass.getUserIds().size());
    }

    /**
     * Tests the {@link TrainerClass#getNumUsers()} method.
     * Verifies that the correct number of users is returned.
     */
    @Test
    public void testGetNumUsers() {
        TrainerClass trainerClass = new TrainerClass(LocalDate.now(), "Pilates", exercises, 7, new ArrayList<>(List.of(401, 402, 403)));
        assertEquals(3, trainerClass.getNumUsers());
    }

    /**
     * Tests the setter and getter methods for trainer ID and user IDs.
     * Ensures that data is updated and retrieved correctly.
     */
    @Test
    public void testSettersAndGetters() {
        TrainerClass trainerClass = new TrainerClass(LocalDate.now(), "Stretching", exercises, 99, new ArrayList<>());
        trainerClass.setTrainerId(55);
        trainerClass.setUserIds(new ArrayList<>(List.of(1, 2, 3)));

        assertEquals(55, trainerClass.getTrainerId());
        assertEquals(3, trainerClass.getUserIds().size());
    }
}
