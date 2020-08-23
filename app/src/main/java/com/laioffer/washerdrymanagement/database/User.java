package com.laioffer.washerdrymanagement.database;

import java.util.Objects;

public class User {
    String username;
    String password;
    String phone_number;
    String background_id;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", background_id='" + background_id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(phone_number, user.phone_number) &&
                Objects.equals(background_id, user.background_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, phone_number, background_id);
    }
}
