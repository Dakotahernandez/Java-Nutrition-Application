import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracking.Workout;
import tracking.Exercise;

import javax.swing.DefaultListModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutTest {

    private Exercise exercise1;
    private Exercise exercise2;
    private List<Exercise> exerciseList;

    @BeforeEach
    public void setUp() {
        exercise1 = new Exercise("Push-ups", "Strength", 20, 10, 50, "Push-up exercise");
        exercise2 = new Exercise("Running", "Cardio", 0, 30, 300, "30-minute run");

        exerciseList = new ArrayList<>();
        exerciseList.add(exercise1);
        exerciseList.add(exercise2);
    }

    @Test
    public void testConstructorWithoutId() {
        LocalDate date = LocalDate.of(2025, 5, 8);
        Workout workout = new Workout(date, "Morning Workout", exerciseList);

        assertEquals(-1, workout.getId());
        assertEquals("Morning Workout", workout.getName());
        assertEquals(date, workout.getDate());
        assertEquals(2, workout.getExercises().size());
    }

    @Test
    public void testConstructorWithId() {
        LocalDate date = LocalDate.of(2025, 5, 8);
        Workout workout = new Workout(101, date, "Evening Workout", exerciseList);

        assertEquals(101, workout.getId());
        assertEquals("Evening Workout", workout.getName());
        assertEquals(date, workout.getDate());
        assertEquals(exerciseList, workout.getExercises());
    }

    @Test
    public void testDefaultConstructor() {
        Workout workout = new Workout();

        assertNotNull(workout.getDate());
        assertNotNull(workout.getExercises());
        assertEquals(0, workout.getExercises().size());
    }

    @Test
    public void testAddExercise() {
        Workout workout = new Workout();
        workout.addExcercise(exercise1);
        assertEquals(1, workout.getExercises().size());
        assertEquals(exercise1, workout.getExercises().get(0));
    }

    @Test
    public void testRemoveExercise() {
        Workout workout = new Workout();
        workout.addExcercise(exercise1);
        workout.removeExercise(exercise1);
        assertEquals(0, workout.getExercises().size());
    }

    @Test
    public void testGetTotalDuration() {
        Workout workout = new Workout();
        workout.addExcercise(exercise1);
        workout.addExcercise(exercise2);
        assertEquals(40, workout.getTotalDuration());
    }

    @Test
    public void testGetTotalCalories() {
        Workout workout = new Workout();
        workout.addExcercise(exercise1);
        workout.addExcercise(exercise2);
        assertEquals(350, workout.getTotalCalories());
    }

    @Test
    public void testGetExerciseCount() {
        Workout workout = new Workout();
        workout.addExcercise(exercise1);
        workout.addExcercise(exercise2);
        assertEquals(2, workout.getExerciseCount());
    }

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
