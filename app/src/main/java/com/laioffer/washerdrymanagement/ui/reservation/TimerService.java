package com.laioffer.washerdrymanagement.ui.reservation;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.laioffer.washerdrymanagement.database.DataRepository;
import com.laioffer.washerdrymanagement.database.Reservation;
import com.laioffer.washerdrymanagement.database.TemporaryType;
import com.laioffer.washerdrymanagement.util.Config;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {
    private Timer timer1;
    private Timer timer2;
    private DataRepository dataRepository;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("aaaa","created service");
        dataRepository = new DataRepository(getApplicationContext());
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                Timestamp t = new Timestamp(System.currentTimeMillis());
//                if (Config.getInstance() != null) {
//                    List<Reservation> res = dataRepository.getReservations(Config.userId).getValue();
//                    if (res != null) {
//                        for (Reservation cur : res) {
//                            if (cur.start_time.before(cur.end_time)) {
//                                cur.start_time = t;
//                                dataRepository.updateReservation(cur);
//                            }
//                        }
//                    }
//                }
                if (ReservationAdapter.reservations != null && ReservationAdapter.reservations.size() != 0) {
                    for (int i = 0; i < ReservationAdapter.reservations.size(); i++) {
                        TemporaryType cur = ReservationAdapter.reservations.get(i);
                        Timestamp curt = new Timestamp(System.currentTimeMillis());
                        String curtime = curt.toString();
                        curtime = curtime.substring(0, curtime.length() - 4);
                        Log.d("aaaa", cur.model);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date startDate = null;
                        Date endDate = null;
                        try {
                            startDate = dateFormat.parse(curtime);
                            endDate = dateFormat.parse(cur.end_time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Timestamp startstamp = new Timestamp(startDate.getTime());
                        Timestamp endstamp = new Timestamp(endDate.getTime());
                        if (!startDate.after(endDate)) {
                            cur.start_time = curtime;
                            ReservationAdapter.reservations.set(i, cur);
                        }
                    }
                }
                Log.d("aaaa", "Timer1 is running" + t);
            }
        }, 0, 5000);
        return super.onStartCommand(intent, flags, startId);
    }
}
