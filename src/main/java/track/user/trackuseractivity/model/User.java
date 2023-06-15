package track.user.trackuseractivity.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private String role;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user",orphanRemoval = true)
    private List<UserActivity> userActivity=new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<UserActivity> getUserActivity() {
        return userActivity;
    }

    public void setUserActivity(List<UserActivity> userActivity) {
        this.userActivity = userActivity;
    }
}
