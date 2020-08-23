package com.laioffer.washerdrymanagement.util;

public class Config {

    public static String username = "Austin";
    private static Config instance = null;


    public static String userId;
    public static String firstName;
    public static String lastName;


    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public static Config getInstance() {
        return instance;
    }

    private Config(String userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static Config getInstance(String userId, String firstName, String lastName) {
        if (instance == null) {
            instance = new Config(userId, firstName, lastName);
        }

        return instance;
    }


}

