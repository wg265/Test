package com.laioffer.washerdrymanagement.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.Objects;
public class Reservation {
    public String end_time;
    public String condition;
    public String address;
    public String item_id;
    public String model;
    public String type;
    public String brand;

}
