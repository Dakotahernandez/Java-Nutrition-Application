/**
 * =============================================================================
 * File:        WorkoutTest.java
 * Authors:     Eli Hall
 * Created:     05/08/2025
 * -----------------------------------------------------------------------------
 * Description:
 *   Unit tests for the Workout class. These tests verify the correct behavior
 *   of the Workout constructor, adding/removing exercises, and calculating
 *   total duration, calories, and exercise count.
 *
 * Dependencies:
 *   - org.junit.jupiter.api.*
 *   - tracking.Workout
 *   - tracking.Exercise
 *   - javax.swing.DefaultListModel
 *   - java.time.LocalDate
 *
 * Usage:
 *   Test the functionality of the Workout class by running the test methods
 *   defined in this class using a test runner.
 *
 * =============================================================================
 */


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracking.Workout;
import tracking.Exercise;

import javax.swing.DefaultListModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Workout} class.
 * These tests verify correct construction, validation, and behavior of workout-related attributes and methods.
 */
public class WorkoutTest {

    private Exercise exercise1;
    private Exercise exercise2;
    private List<Exercise> exerciseList;

    /**
     * Sets up the test data for each test method.
     * Initializes two exercises and a list containing both exercises.
     */
    @BeforeEach
    public void setUp() {
        exercise1 = new Exercise("Push-ups", "Strength", 20, 10, 50, "Push-up exercise");
        exercise2 = new Exercise("Running", "Cardio", 0, 30, 300, "30-minute run");

        exerciseList = new ArrayList<>();
        exerciseList.add(exercise1);
        exerciseList.add(exercise2);
    }

    /**
     * Tests the constructor of the {@link Workout} class without an ID.
     * Verifies the ID is set to -1 and the workout name, date, and exercises are correctly initialized.
     */
    @Test
    public void testConstructorWithoutId() {
        LocalDate date = LocalDate.of(2025, 5, 8);
        Workout workout = new Workout(date, "Morning Workout", exerciseList);

        assertEquals(-1, workout.getId());
        assertEquals("Morning Workout", workout.getName());
        assertEquals(date, workout.getDate());
        assertEquals(2, workout.getExercises().size());
    }

    /**
     * Tests the constructor of the {@link Workout} class with an ID.
     * Verifies that the workout's ID, name, date, and exercise list are initialized correctly.
     */
    @Test
    public void testConstructorWithId() {
        LocalDate date = LocalDate.of(2025, 5, 8);
        Workout workout = new Workout(101, date, "Evening Workout", exerciseList);

        assertEquals(101, workout.getId());
        assertEquals("Evening Workout", workout.getName());
        assertEquals(date, workout.getDate());
        assertEquals(exerciseList, workout.getExercises());
    }

    /**
     * Tests the default constructor of the {@link Workout} class.
     * Verifies that the workout date and exercises list are initialized and empty.
     */
    @Test
    public void testDefaultConstructor() {
        Workout workout = new Workout();

        assertNotNull(workout.getDate());
        assertNotNull(workout.getExercises());
        assertEquals(0, workout.getExercises().size());
    }

    /**
     * Tests adding an exercise to the workout.
     * Verifies that the exercise is added correctly and the exercise count increases.
     */
    @Test
    public void testAddExercise() {
        Workout workout = new Workout();
        workout.addExcercise(exercise1);
        assertEquals(1, workout.getExercises().size());
        assertEquals(exercise1, workout.getExercises().get(0));
    }

    /**
     * Tests removing an exercise from the workout.
     * Verifies that the exercise is removed correctly and the exercise count decreases.
     */
    @Test
    public void testRemoveExercise() {
        Workout workout = new Workout();
        workout.addExcercise(exercise1);
        workout.removeExercise(exercise1);
        assertEquals(0, workout.getExercises().size());
    }

    /**
     * Tests the calculation of the total duration of all exercises in the workout.
     * Verifies the correct total duration based on the exercises' individual durations.
     */
    @Test
    public void testGetTotalDuration() {
        Workout workout = new Workout();
        workout.addExcercise(exercise1);
        workout.addExcercise(exercise2);
        assertEquals(40, workout.getTotalDuration());
    }

    /**
     * Tests the calculation of the total calories burned during the workout.
     * Verifies the correct total calories based on the exercises' individual calories.
     */
    @Test
    public void testGetTotalCalories() {
        Workout workout = new Workout();
        workout.addExcercise(exercise1);
        workout.addExcercise(exercise2);
        assertEquals(350, workout.getTotalCalories());
    }

    /**
     * Tests getting the total number of exercises in the workout.
     * Verifies the correct count of exercises added to the workout.
     */
    @Test
    public void testGetExerciseCount() {
        Workout workout = new Workout();
        workout.addExcercise(exercise1);
        workout.addExcercise(exercise2);
        assertEquals(2, workout.getExerciseCount());
    }

    /**
     * Tests getting a {@link DefaultListModel} of exercises in the workout.
     * Verifies that the list model contains the correct exercises in the correct order.
     */
    @Test
    public void testGetDefaultListModel() {
        Workout workout = new Workout();
        workout.addExcercise(exercise1);
        workout.addExcercise(exercise2);
        DefaultListModel<Exercise> model = workout.getDefaultListModel();

        assertEquals(2, model.getSize());
        assertEquals(exercise1, model.getElementAt(0));
        assertEquals(exercise2, model.getElementAt(1));
    }

    /**
     * Tests the setter methods of the {@link Workout} class.
     * Verifies that the workout ID, name, date, and exercises can be set and retrieved correctly.
     */
    @Test
    public void testSetters() {
        Workout workout = new Workout();
        LocalDate date = LocalDate.of(2025, 1, 1);
        workout.setId(5);
        workout.setName("Updated Workout");
        workout.setDate(date);
        workout.setExercises(exerciseList);

        assertEquals(5, workout.getId());
        assertEquals("Updated Workout", workout.getName());
        assertEquals(date, workout.getDate());
        assertEquals(2, workout.getExercises().size());
    }
}
