package com.trail.musicalhost.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "musicfile")
public class Music {


    @Transient
    private static final String SEQUENCE_NAME="user_sequence";
    @Id
    private int musicId;
    private String name;
    private String description;
    private String type;
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    private byte[] data;
    public int getMusicId() {
        return musicId;
    }
    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }
    public byte[] getData() {
        return data;
    }
    //Constructors
    public Music(){
        super();
    }
    public Music(  String name, String type,byte[] data) {
        this.name = name;
        this.type = type;
        this.data=data;
    }
    @Override
    public String toString() {
        return "Music{" +
                "musicId=" + musicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }

    //Relation
    @JsonIgnore
    @JsonIgnoreProperties(allowSetters = true)
    private User user;
    //Getters and Setters
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
