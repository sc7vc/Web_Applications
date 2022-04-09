package com.example.icp10;
import com.google.gson.annotations.SerializedName;
public class GithubUser {
    private int id;

    @SerializedName("login")
    private String username;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;

    }
}

