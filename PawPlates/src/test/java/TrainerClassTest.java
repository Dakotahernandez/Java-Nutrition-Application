import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracking.Exercise;
import tracking.TrainerClass;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrainerClassTest {

    private Exercise exercise1;
    private Exercise exercise2;
    private List<Exercise> exercises;
    private List<Integer> userIds;

    @BeforeEach
    public void setUp() {
        exercise1 = new Exercise("Squats", "Strength", 15, 10, 60, "Basic squats");
        exercise2 = new Exercise("Cycling", "Cardio", 0, 20, 200, "Stationary cycling");

        exercises = new ArrayList<>(Arrays.asList(exercise1, exercise2));
        userIds = new ArrayList<>(Arrays.asList(101, 102));
    }

    @Test
    public void testConstructorWithId() {
        TrainerClass trainerClass = new TrainerClass(1, LocalDate.of(2025, 5, 8), "Bootcamp", exercises, 10, userIds);

        assertEquals(1, trainerClass.getId());
        assertEquals("Bootcamp", trainerClass.getName());
        assertEquals(10, trainerClass.getTrainerId());
        assertEquals(2, trainerClass.getUserIds().size());
    }

    @Test
    public void testConstructorWithoutId() {
        TrainerClass trainerClass = new TrainerClass(LocalDate.of(2025, 5, 8), "HIIT", exercises, 20, userIds);

        assertEquals("HIIT", trainerClass.getName());
        assertEquals(20, trainerClass.getTrainerId());
        assertEquals(2, trainerClass.getUserIds().size());
    }

    @Test
    public void testAddUserId() {
        TrainerClass trainerClass = new TrainerClass(LocalDate.now(), "Yoga", exercises, 5, new ArrayList<>());

        assertTrue(trainerClass.addUserId(200));
        assertFalse(trainerClass.addUserId(200)); // no duplicates
        assertEquals(1, trainerClass.getUserIds().size());
    }

    @Test
    public void testRemoveUserId() {
        TrainerClass trainerClass = new TrainerClass(LocalDate.now(), "Zumba", exercises, 5, new ArrayList<>(List.of(300, 301)));

        assertTrue(trainerClass.removeUserId(300));
        assertFalse(trainerClass.removeUserId(400)); // not in list
        assertEquals(1, trainerClass.getUserIds().size());
    }

    @Test
    public void testGetNumUsers() {
        TrainerClass trainerClass = new TrainerClass(LocalDate.now(), "Pilates", exercises, 7, new ArrayList<>(List.of(401, 402, 403)));
        assertEquals(3, trainerClass.getNumUsers());
    }

    @Test
    public void testSettersAndGetters() {
        TrainerClass trainerClass = new TrainerClass(LocalDate.now(), "Stretching", exercises, 99, new ArrayList<>());
        trainerClass.setTrainerId(55);
        trainerClass.setUserIds(new ArrayList<>(List.of(1, 2, 3)));

        assertEquals(55, trainerClass.getTrainerId());
        assertEquals(3, trainerClass.getUserIds().size());
    }
}
