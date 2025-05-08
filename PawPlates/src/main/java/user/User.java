package user;
/**
 * =============================================================================
 * File:           user.User.java
 * Authors:        Mac Johnson Faith Ota Dakota Hernandez
 * Created:        05/08/25
 * -----------------------------------------------------------------------------
 * Description:
 *   Provides a basic structure for all users in the application, including
 *   login credentials, email, ID, and trainer status. Includes input validation
 *   for email and utility methods for accessing and modifying user data.
 *
 * Dependencies:
 *   - None (standard Java libraries only)
 *
 * Usage:
 *   // Create a basic user
 *   User user = new User("username", "password");
 *
 *   // Set additional attributes
 *   user.setEmail("user@example.com");
 *   user.setTrainer(true);
 *
 * TODO:
 *   - Integrate password encryption
 * =============================================================================
 */
/*
 * PURPOSE: provide a basic structure for ALL users (all users need a login)
 */
public class User {
    String username;
    String password;
    String email;
    int id;
    boolean isTrainer = false;
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public String getUsername() {
        return username;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public String getPassword() {
        return password;
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IllegalArgumentException {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        if (email.length() > 75) {
            throw new IllegalArgumentException("Email cannot be more than 75 characters.");
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[\\w]+$")) {
            throw new IllegalArgumentException("Email address is invalid.");
        }
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isTrainer() {
        return isTrainer;
    }

    public void setTrainer(boolean trainer) {
        isTrainer = trainer;
    }
}
