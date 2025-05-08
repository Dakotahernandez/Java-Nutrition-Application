/**
 * =============================================================================
 * File: TrainerClass.java
 * Author: Joshua Carroll
 * Created: 5/8/2025
 * -----------------------------------------------------------------------------
 * Description:
 * Extends the Workout class to represent a group class led by a trainer.
 * Adds trainer ID and a list of registered user IDs. Used for managing
 * scheduled group workouts where users can register/unregister.
 *
 * Dependencies:
 * - java.time.LocalDate
 * - java.util.List
 * - tracking.Workout
 * - tracking.Exercise
 *
 * Usage:
 * TrainerClass tc = new TrainerClass(date, "Bootcamp", exercises, trainerId, userIds);
 * =============================================================================
 */

package tracking;

import java.time.LocalDate;
import java.util.List;

public class TrainerClass extends Workout {
    private int trainerId;
    private List<Integer> userIds;

    /**
     * Constructs a TrainerClass with an existing ID.
     *
     * @param id         the unique class ID
     * @param date       the date of the class
     * @param name       the name of the class
     * @param exercises  list of exercises included
     * @param trainerId  ID of the trainer leading the class
     * @param userIds    list of registered user IDs
     */
    public TrainerClass(int id, LocalDate date, String name, List<Exercise> exercises, int trainerId, List<Integer> userIds) {
        super(id, date, name, exercises);
        this.trainerId = trainerId;
        this.userIds = userIds;
    }

    /**
     * Constructs a new TrainerClass without specifying an ID.
     *
     * @param date       the date of the class
     * @param name       the name of the class
     * @param exercises  list of exercises included
     * @param trainerId  ID of the trainer leading the class
     * @param userIds    list of registered user IDs
     */
    public TrainerClass(LocalDate date, String name, List<Exercise> exercises, int trainerId, List<Integer> userIds) {
        super(date, name, exercises);
        this.trainerId = trainerId;
        this.userIds = userIds;
    }

    /**
     * Returns the ID of the trainer.
     *
     * @return the trainer ID
     */
    public int getTrainerId() {
        return trainerId;
    }

    /**
     * Sets the trainer ID.
     *
     * @param trainerId the new trainer ID
     */
    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    /**
     * Returns the list of registered user IDs.
     *
     * @return list of user IDs
     */
    public List<Integer> getUserIds() {
        return userIds;
    }

    /**
     * Sets the list of registered user IDs.
     *
     * @param userIds the new list of user IDs
     */
    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    /**
     * Adds a user ID to the list if not already registered.
     *
     * @param userId the user ID to add
     * @return true if the user was added; false if already present
     */
    public boolean addUserId(int userId) {
        if (!userIds.contains(userId)) {
            userIds.add(userId);
            return true;
        }
        return false;
    }

    /**
     * Removes a user ID from the list.
     *
     * @param userId the user ID to remove
     * @return true if the user was removed; false if not found
     */
    public boolean removeUserId(int userId) {
        return userIds.remove(Integer.valueOf(userId));
    }

    /**
     * Returns the number of registered users in the class.
     *
     * @return the size of the user ID list
     */
    public int getNumUsers() {
        return userIds.size();
    }
}