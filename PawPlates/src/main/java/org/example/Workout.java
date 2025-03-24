package org.example;

import java.sql.Time;
import java.util.Date;

/*
 * PURPOSE: Be able to create Workout instances to add it to a user's progress
 * COMPLETED: constructor, getter/setter methods, ...
 */

public class Workout {
    private Date date;
    private String focus;
    private Time duration;

    //Setter Methods:
    public void setDate(Date date) { this.date = date; }
    public void setDuration(Time duration) { this.duration = duration; }
    public void setFocus(String focus) { this.focus = focus; }

    //Getter Methods:
    public Date getDate() { return date; }
    public String getFocus() { return focus; }
    public Time getDuration() { return duration; }

    //Parameterized Constructor
    Workout(Date date, String focus, Time duration){
        this.date = date;
        this.focus = focus;
        this.duration = duration;
    }
}
