package com.laioffer.washerdrymanagement.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

public class Item implements Serializable {
    public String item_id;
   public String type;
   public String model;
   public String brand;
   public String condition;
   public String address;
   public String end_time;

}
