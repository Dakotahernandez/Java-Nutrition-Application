/**
 * =============================================================================
 * File:        UserTest.java
 * Authors:     Eli Hall
 * Created:     05/08/2025
 * -----------------------------------------------------------------------------
 * Description:
 *   Unit tests for the User class. These tests verify the correct functionality
 *   of user account creation, email validation, and trainer flag functionality.
 *
 * Dependencies:
 *   - org.junit.jupiter.api.*
 *   - user.User
 *
 * Usage:
 *   Test the functionality of the User class by running the test methods
 *   defined in this class using a test runner.
 *
 * =============================================================================
 */


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import user.User;

/**
 * Unit tests for the {@link User} class.
 * These tests verify correct construction, validation,
 * and behavior of user attributes such as email, ID, and trainer flag.
 */
public class UserTest {

    /**
     * Tests constructor with only username and password.
     * Verifies that username and password are set, and email is null.
     */
    @Test
    public void testConstructorWithUsernameAndPassword() {
        User user = new User("john_doe", "password123");
        assertEquals("john_doe", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertNull(user.getEmail());
    }

    /**
     * Tests constructor with username, password, and email.
     * Verifies all fields are properly set.
     */
    @Test
    public void testConstructorWithUsernamePasswordAndEmail() {
        User user = new User("jane_doe", "securepass", "jane@example.com");
        assertEquals("jane_doe", user.getUsername());
        assertEquals("securepass", user.getPassword());
        assertEquals("jane@example.com", user.getEmail());
    }

    /**
     * Tests setting a valid email address.
     * Verifies the email is stored correctly.
     */
    @Test
    public void testSetEmailValid() {
        User user = new User("user", "pass");
        user.setEmail("valid.email@domain.com");
        assertEquals("valid.email@domain.com", user.getEmail());
    }

    /**
     * Tests setting a null email.
     * Expects an {@link IllegalArgumentException} with a specific message.
     */
    @Test
    public void testSetEmailNullThrowsException() {
        User user = new User("user", "pass");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setEmail(null);
        });
        assertEquals("Email cannot be empty.", exception.getMessage());
    }

    /**
     * Tests setting an excessively long email address (>75 characters).
     * Expects an {@link IllegalArgumentException} with a specific message.
     */
    @Test
    public void testSetEmailTooLongThrowsException() {
        User user = new User("user", "pass");
        String longEmail = "a".repeat(76) + "@test.com";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setEmail(longEmail);
        });
        assertEquals("Email cannot be more than 75 characters.", exception.getMessage());
    }

    /**
     * Tests setting an invalid email format.
     * Expects an {@link IllegalArgumentException} with a specific message.
     */
    @Test
    public void testSetEmailInvalidFormatThrowsException() {
        User user = new User("user", "pass");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setEmail("invalid-email-format");
        });
        assertEquals("Email address is invalid.", exception.getMessage());
    }

    /**
     * Tests setting and retrieving the user ID.
     */
    @Test
    public void testSetAndGetId() {
        User user = new User("user", "pass");
        user.setId(42);
        assertEquals(42, user.getId());
    }

    /**
     * Tests the trainer flag for the user.
     * Verifies default value is false and that it can be updated.
     */
    @Test
    public void testTrainerFlag() {
        User user = new User("user", "pass");
        assertFalse(user.isTrainer());
        user.setTrainer(true);
        assertTrue(user.isTrainer());
    }
}
