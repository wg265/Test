package com.laioffer.washerdrymanagement.database;

import java.util.Objects;

public class BackGround {
    public String first_name;
    public String last_name;
    public String email;
    public String about_me;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BackGround that = (BackGround) o;
        return Objects.equals(first_name, that.first_name) &&
                Objects.equals(last_name, that.last_name) &&
                Objects.equals(email, that.email) &&
                Objects.equals(about_me, that.about_me);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first_name, last_name, email, about_me);
    }

    @Override
    public String toString() {
        return "BackGround{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", about_me='" + about_me + '\'' +
                '}';
    }
}
