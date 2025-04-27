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

/*
 * PURPOSE: Be able to create workout.Workout instances to add it to a user's progress
 * COMPLETED: constructor, getter/setter methods, ...
 */

public class Workout {
    private Date date;
    private String focus;
    private Time duration;

    //Setter Methods:
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void setDate(Date date) { this.date = date; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void setDuration(Time duration) { this.duration = duration; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public void setFocus(String focus) { this.focus = focus; }

    //Getter Methods:
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public Date getDate() { return date; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public String getFocus() { return focus; }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public Time getDuration() { return duration; }

    //Parameterized Constructor
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    Workout(Date date, String focus, Time duration){
        this.date = date;
        this.focus = focus;
        this.duration = duration;
    }

    //Create a workout.Workout
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    Workout createWorkout(Date date, String focus, Time duration){
        Workout w = new Workout(date, focus, duration);
        return w;
    }
}
