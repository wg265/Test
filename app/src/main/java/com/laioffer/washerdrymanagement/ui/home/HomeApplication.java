//package com.laioffer.washerdrymanagement.ui.home;
//
//import android.app.Application;
//import android.util.Log;
//
//import androidx.room.Room;
//
////import com.laioffer.washerdrymanagement.database.ReservationDatabase;
//
//public class HomeApplication extends Application {
//    private ReservationDatabase reservationDatabase;
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.d("aaaa", "creating database");
//        reservationDatabase = Room.databaseBuilder(this, ReservationDatabase.class, "reservation_db").build();
//    }
//    public ReservationDatabase getDatabase() {
//        return reservationDatabase;
//    }
//}
