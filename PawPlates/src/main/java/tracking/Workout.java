package tracking; /**
 * =============================================================================
 * File:
 * Author:
 * Created:
 * -----------------------------------------------------------------------------
 * Description:
 *
 *
 * Dependencies:
 *
 *
 * Usage:
 *
 * =============================================================================
 */
import javax.swing.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.*;

/*
 * PURPOSE: Be able to create workout.Workout instances to add it to a user's progress
 * COMPLETED: constructor, getter/setter methods, ...
 */

public class Workout {
    private int id;
    private String name;
    private LocalDate date;
    private List<Exercise> exercises;


    //Parameterized Constructor
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public Workout(LocalDate date, String name,  List<Exercise> exercises) {
        id = -1;
        this.date = date;
        this.name = name;
        this.exercises = exercises;
    }

    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public Workout(int id, LocalDate date, String name, List<Exercise> exercises) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.exercises = exercises;
    }

    public Workout(){
        date = LocalDate.now();
        exercises = new ArrayList<>();
    }

    public DefaultListModel<Exercise> getDefaultListModel(){
        DefaultListModel<Exercise> model = new DefaultListModel<>();
        exercises.forEach(model::addElement);
        return model;
    }

    public int getTotalDuration(){
        return exercises.stream().mapToInt(Exercise::getDuration).sum();
    }
    public int getTotalCalories() {
        return exercises.stream()
                .mapToInt(e -> e.getCaloriesBurned())
                .sum();
    }
    public int getExerciseCount() {
        return exercises.size();
    }

    public void addExcercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public void removeExercise(Exercise exercise){
        exercises.remove(exercise);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    //Setter Methods:
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void setDate(LocalDate date) { this.date = date; }


    //Getter Methods:
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public LocalDate getDate() { return date; }



}
