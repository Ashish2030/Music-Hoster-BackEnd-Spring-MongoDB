package com.trail.musicalhost.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;
@Document(collection = "musicusertable")
public class User {
    @Id
    private int id;
    private String userName;
    private String password;
    private String role;
    private boolean active;

    private UserProfile profile;

    @DBRef
    private List<Music> music= new ArrayList<>();

    //Constructors
    public User(){
        super();
    }

    public User(int id, String userName, String password, String role, boolean active, UserProfile profile) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.active = active;
        this.profile = profile;
    }
    public User(User user) {
        this.id = user.id;
        this.userName = user.userName;
        this.password = user.password;
        this.role = user.role;
        this.active = user.active;
        this.profile = user.profile;
        this.music = user.music;
    }
    //Getters and Setters
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", active=" + active +
                ", profile=" + profile +
                ", music=" + music.size() +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public List<Music> getMusic() {
        return music;
    }

    public void setMusic(List<Music> music) {
        this.music = music;
    }
}
