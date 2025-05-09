import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import user.User;

public class UserTest {

    @Test
    public void testConstructorWithUsernameAndPassword() {
        User user = new User("john_doe", "password123");
        assertEquals("john_doe", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertNull(user.getEmail());
    }

    @Test
    public void testConstructorWithUsernamePasswordAndEmail() {
        User user = new User("jane_doe", "securepass", "jane@example.com");
        assertEquals("jane_doe", user.getUsername());
        assertEquals("securepass", user.getPassword());
        assertEquals("jane@example.com", user.getEmail());
    }

    @Test
    public void testSetEmailValid() {
        User user = new User("user", "pass");
        user.setEmail("valid.email@domain.com");
        assertEquals("valid.email@domain.com", user.getEmail());
    }

    @Test
    public void testSetEmailNullThrowsException() {
        User user = new User("user", "pass");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setEmail(null);
        });
        assertEquals("Email cannot be empty.", exception.getMessage());
    }

    @Test
    public void testSetEmailTooLongThrowsException() {
        User user = new User("user", "pass");
        String longEmail = "a".repeat(76) + "@test.com";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setEmail(longEmail);
        });
        assertEquals("Email cannot be more than 75 characters.", exception.getMessage());
    }

    @Test
    public void testSetEmailInvalidFormatThrowsException() {
        User user = new User("user", "pass");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setEmail("invalid-email-format");
        });
        assertEquals("Email address is invalid.", exception.getMessage());
    }

    @Test
    public void testSetAndGetId() {
        User user = new User("user", "pass");
        user.setId(42);
        assertEquals(42, user.getId());
    }

    @Test
    public void testTrainerFlag() {
        User user = new User("user", "pass");
        assertFalse(user.isTrainer());
        user.setTrainer(true);
        assertTrue(user.isTrainer());
    }
}
