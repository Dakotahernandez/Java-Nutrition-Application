package tracking; /**
 * =============================================================================
 * File: Exercise.java
 * Author: Joshua Carroll
 * Created:3/8/2025
 * -----------------------------------------------------------------------------
 * Description:
 * Represents an exercise performed during a workout. Tracks attributes such as
 * name, focus (e.g., cardio, weight training), duration, repetitions, calories burned,
 * and a description. Used for tracking workout details in the application.
 *
 * Dependencies:
 * java.util.Objects
 *
 * Usage:
 * Exercise e = new Exercise("Push-up", "Strength", 15, 5, 50, "Upper body strength exercise");
 * =============================================================================
 */

import java.util.Objects;

public class Exercise {
    int id;
    private String name;
    private String focus;
    private String description;
    private int duration;
    private int reps;
    private int caloriesBurned;

    /**
     * Default constructor that initializes all fields to default values.
     */
    public Exercise() {
        name ="";
        focus = "";
        description = "";
        duration = 0;
        caloriesBurned = 0;
    }
    /**
     * Constructs an Exercise object without a specified ID.
     *
     * @param name            the name of the exercise
     * @param focus           the focus category (e.g., cardio, strength)
     * @param reps            the number of repetitions
     * @param duration        the duration in minutes
     * @param caloriesBurned  estimated calories burned during the exercise
     * @param description     a textual description of the exercise
     */
    public Exercise(String name, String focus, int reps, int duration, int caloriesBurned,String description) {
        this.id = -1;
        this.name = name;
        this.focus = focus;
        this.description = description;
        this.reps = reps;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
    }
    /**
     * Constructs an Exercise object with a specified ID.
     *
     * @param id              the unique identifier for the exercise
     * @param name            the name of the exercise
     * @param focus           the focus category (e.g., cardio, strength)
     * @param reps            the number of repetitions
     * @param duration        the duration in minutes
     * @param caloriesBurned  estimated calories burned during the exercise
     * @param description     a textual description of the exercise
     */
    public Exercise(int id, String name, String focus, int reps, int duration, int caloriesBurned,String description) {
        this.id = id;
        this.name = name;
        this.focus = focus;
        this.description = description;
        this.reps = reps;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
    }
    /**
     * Returns the exercise ID.
     *
     * @return the ID of the exercise
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the exercise ID.
     *
     * @param id the new ID to assign
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the exercise.
     *
     * @return the exercise name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the exercise.
     *
     * @param name the new exercise name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the focus of the exercise.
     *
     * @return the focus category (e.g., cardio, strength)
     */
    public String getFocus() {
        return focus;
    }

    /**
     * Sets the focus category of the exercise.
     *
     * @param focus the new focus category
     */
    public void setFocus(String focus) {
        this.focus = focus;
    }

    /**
     * Returns the description of the exercise.
     *
     * @return the textual description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the exercise.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the duration of the exercise.
     *
     * @return the duration in minutes
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the exercise.
     *
     * @param duration the new duration in minutes
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Returns the number of calories burned.
     *
     * @return calories burned during the exercise
     */
    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    /**
     * Sets the calories burned.
     *
     * @param caloriesBurned the new calorie value
     */
    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    /**
     * Returns the number of repetitions.
     *
     * @return the number of reps
     */
    public int getReps() {
        return reps;
    }

    /**
     * Sets the number of repetitions.
     *
     * @param reps the new rep count
     */
    public void setReps(int reps) {
        this.reps = reps;
    }

    /**
     * Checks equality between this Exercise and another object.
     *
     * @param o the object to compare to
     * @return true if the objects are equal based on field values; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return duration == exercise.duration &&
                caloriesBurned == exercise.caloriesBurned &&
                Objects.equals(name, exercise.name) &&
                Objects.equals(focus, exercise.focus) &&
                Objects.equals(description, exercise.description);
    }

    /**
     * Generates a hash code based on the exercise's fields.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, focus, description, duration, caloriesBurned);
    }

    /**
     * Returns the string representation of the exercise.
     *
     * @return the exercise name
     */
    @Override
    public String toString() {
        return name;
    }
}