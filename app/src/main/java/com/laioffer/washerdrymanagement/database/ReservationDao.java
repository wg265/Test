//package com.laioffer.washerdrymanagement.database;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import java.util.List;
//
//@Dao
//public interface ReservationDao {
//    @Update
//    void updatetime(Reservation reservation);
//
//    @Insert
//    void newReservation(Reservation reservation);
//
//    @Query("SELECT * FROM Reservation where item_id like :item_id")
//    List<Reservation> getLocalReservation(String item_id);
//
//    @Query("DELETE FROM Reservation where item_id = :item_id")
//    void deleteReservation(String item_id);
//}
