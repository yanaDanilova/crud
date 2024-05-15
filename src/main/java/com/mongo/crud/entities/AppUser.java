package com.mongo.crud.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document (collection = "users")
public class AppUser {

    @Id
    private String userId;

    @Field("user_name")
    private String username;

    private String dob;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getDob() {
        return dob;
    }
}
