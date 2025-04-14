/*
 * PURPOSE: provide a basic structure for ALL users (all users need a login)
 */
public class User {
    String username;
    String password;
    String email;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
