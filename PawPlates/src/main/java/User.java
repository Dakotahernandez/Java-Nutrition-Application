/*
 * PURPOSE: provide a basic structure for ALL users (all users need a login)
 */
public abstract class User {
    String username;
    String password;

    final void login(String username, String password){
        this.username = username;
        this.password = password;
    }

    final void setUsername(String username){
        this.username = username;
    }
    final void setPassword(String password){
        this.password = password;
    }
}
