package tracking;

import java.time.LocalDate;
import java.util.List;

public class TrainerClass extends Workout {
    private int trainerId;
    private List<Integer> userIds;

    public TrainerClass(int id, LocalDate date, String name, List<Exercise> exercises, int trainerId, List<Integer> userIds) {
        super(id, date, name, exercises);
        this.trainerId = trainerId;
        this.userIds = userIds;
    }

    public TrainerClass(LocalDate date, String name, List<Exercise> exercises, int trainerId, List<Integer> userIds) {
        super(date, name, exercises);
        this.trainerId = trainerId;
        this.userIds = userIds;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public boolean addUserId(int userId) {
        if (!userIds.contains(userId)) {
            userIds.add(userId);
            return true;
        }
        return false;
    }

    public boolean removeUserId(int userId) {
        return userIds.remove(Integer.valueOf(userId));
    }

    public int getNumUsers() {
        return userIds.size();
    }
}