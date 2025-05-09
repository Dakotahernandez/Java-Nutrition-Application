import org.junit.jupiter.api.Test;
import tracking.Exercise;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseTest {

    @Test
    public void testDefaultConstructor() {
        Exercise ex = new Exercise();
        assertEquals("", ex.getName());
        assertEquals("", ex.getFocus());
        assertEquals("", ex.getDescription());
        assertEquals(0, ex.getDuration());
        assertEquals(0, ex.getCaloriesBurned());
    }

    @Test
    public void testConstructorWithNoId() {
        Exercise ex = new Exercise("Push Up", "Chest", 20, 10, 100, "Upper body strength");
        assertEquals("Push Up", ex.getName());
        assertEquals("Chest", ex.getFocus());
        assertEquals(20, ex.getReps());
        assertEquals(10, ex.getDuration());
        assertEquals(100, ex.getCaloriesBurned());
        assertEquals("Upper body strength", ex.getDescription());
        assertEquals(-1, ex.getId()); // from constructor
    }

    @Test
    public void testConstructorWithId() {
        Exercise ex = new Exercise(5, "Squat", "Legs", 30, 15, 150, "Leg exercise");
        assertEquals(5, ex.getId());
        assertEquals("Squat", ex.getName());
    }

    @Test
    public void testSettersAndGetters() {
        Exercise ex = new Exercise();
        ex.setName("Jumping Jacks");
        ex.setFocus("Cardio");
        ex.setDescription("Warm-up");
        ex.setDuration(5);
        ex.setReps(50);
        ex.setCaloriesBurned(80);
        ex.setId(99);

        assertEquals("Jumping Jacks", ex.getName());
        assertEquals("Cardio", ex.getFocus());
        assertEquals("Warm-up", ex.getDescription());
        assertEquals(5, ex.getDuration());
        assertEquals(50, ex.getReps());
        assertEquals(80, ex.getCaloriesBurned());
        assertEquals(99, ex.getId());
    }

    @Test
    public void testEqualsAndHashCode() {
        Exercise ex1 = new Exercise("Sit Ups", "Abs", 25, 10, 90, "Core exercise");
        Exercise ex2 = new Exercise("Sit Ups", "Abs", 25, 10, 90, "Core exercise");

        assertEquals(ex1, ex2);
        assertEquals(ex1.hashCode(), ex2.hashCode());
    }

    @Test
    public void testToString() {
        Exercise ex = new Exercise("Burpees", "Full Body", 10, 5, 120, "Explosive movement");
        assertEquals("Burpees", ex.toString());
    }
}
