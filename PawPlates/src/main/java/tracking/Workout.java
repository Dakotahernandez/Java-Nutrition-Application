package tracking;

/**
 * =============================================================================
 * File: Workout.java
 * Author: Joshua Carroll
 * Created: 3/29/2025
 * -----------------------------------------------------------------------------
 * Description:
 * Represents a workout session that consists of multiple exercises. Each workout
 * has a name, date, and a list of associated Exercise instances. It provides utility
 * methods to calculate total duration, calories burned, and number of exercises.
 *
 * Dependencies:
 * - java.time.LocalDate
 * - java.util.List
 * - javax.swing.DefaultListModel
 *
 * Usage:
 * Workout w = new Workout(LocalDate.now(), "Morning Routine", new ArrayList<>());
 * =============================================================================
 */

import javax.swing.*;
import java.time.LocalDate;
import java.util.*;

public class Workout {
    private int id;
    private String name;
    private LocalDate date;
    private List<Exercise> exercises;

    /**
     * Constructs a Workout object without an ID.
     *
     * @param date      the date of the workout
     * @param name      the name of the workout
     * @param exercises a list of exercises included in the workout
     */
    public Workout(LocalDate date, String name, List<Exercise> exercises) {
        id = -1;
        this.date = date;
        this.name = name;
        this.exercises = exercises;
    }

    /**
     * Constructs a Workout object with a specified ID.
     *
     * @param id        the unique identifier for the workout
     * @param date      the date of the workout
     * @param name      the name of the workout
     * @param exercises a list of exercises included in the workout
     */
    public Workout(int id, LocalDate date, String name, List<Exercise> exercises) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.exercises = exercises;
    }

    /**
     * Default constructor initializing the date to today and an empty exercise list.
     */
    public Workout() {
        date = LocalDate.now();
        exercises = new ArrayList<>();
    }

    /**
     * Returns a DefaultListModel containing the workout's exercises,
     * useful for GUI components like JList.
     *
     * @return a DefaultListModel of exercises
     */
    public DefaultListModel<Exercise> getDefaultListModel() {
        DefaultListModel<Exercise> model = new DefaultListModel<>();
        exercises.forEach(model::addElement);
        return model;
    }

    /**
     * Calculates the total duration of all exercises in the workout.
     *
     * @return the total duration in minutes
     */
    public int getTotalDuration() {
        return exercises.stream().mapToInt(Exercise::getDuration).sum();
    }

    /**
     * Calculates the total calories burned in the workout.
     *
     * @return the total calories burned
     */
    public int getTotalCalories() {
        return exercises.stream().mapToInt(Exercise::getCaloriesBurned).sum();
    }

    /**
     * Returns the number of exercises in the workout.
     *
     * @return the count of exercises
     */
    public int getExerciseCount() {
        return exercises.size();
    }

    /**
     * Adds an exercise to the workout.
     *
     * @param exercise the Exercise to add
     */
    public void addExcercise(Exercise exercise) {
        exercises.add(exercise);
    }

    /**
     * Removes an exercise from the workout.
     *
     * @param exercise the Exercise to remove
     */
    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
    }

    /**
     * Returns the workout ID.
     *
     * @return the ID of the workout
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the workout ID.
     *
     * @param id the new workout ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the workout name.
     *
     * @return the name of the workout
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the workout name.
     *
     * @param name the new name of the workout
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the list of exercises in the workout.
     *
     * @param exercises the new list of exercises
     */
    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    /**
     * Returns the list of exercises in the workout.
     *
     * @return the list of Exercise objects
     */
    public List<Exercise> getExercises() {
        return exercises;
    }

    /**
     * Sets the date of the workout.
     *
     * @param date the new workout date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the date of the workout.
     *
     * @return the workout date
     */
    public LocalDate getDate() {
        return date;
    }
}
