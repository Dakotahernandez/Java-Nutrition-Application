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
import java.sql.Time;
import java.util.Date;
import java.util.*;

/*
 * PURPOSE: Be able to create workout.Workout instances to add it to a user's progress
 * COMPLETED: constructor, getter/setter methods, ...
 */

public class Workout {
    private Date date;
    private List<Exercise> exercises;


    //Parameterized Constructor
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public Workout(Date date, String focus, List<Exercise> exercises) {
        this.date = date;
        this.exercises = exercises;
    }

    public void addExcercise(Exercise exercise) {
        exercises.add(exercise);
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
    public void setDate(Date date) { this.date = date; }


    //Getter Methods:
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public Date getDate() { return date; }



}
