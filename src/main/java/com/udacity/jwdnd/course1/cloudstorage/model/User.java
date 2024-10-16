package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String encodedkey;
    private String firstName;
    private String lastName;

    public User(Integer userId, String username, String password, String encodedkey, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.encodedkey= encodedkey;
    }
}


