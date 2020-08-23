package com.laioffer.washerdrymanagement.ui.register;

import com.google.gson.annotations.SerializedName;

public class RegisterEvent {
    @SerializedName("user_id")
    final String userId;
    @SerializedName("password")
    final String password;
    @SerializedName("first_name")
    final String firstName;
    @SerializedName("last_name")
    final String lastName;
    @SerializedName("phone_number")
    final String phoneNumber;

    RegisterEvent(String userId, String password, String firstName, String lastName, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
