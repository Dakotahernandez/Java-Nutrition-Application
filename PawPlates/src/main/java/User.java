/**
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
/*
 * PURPOSE: provide a basic structure for ALL users (all users need a login)
 */
public class User {
    String username;
    String password;
    String email;
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
}
