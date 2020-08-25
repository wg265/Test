package com.laioffer.washerdrymanagement.ui.reservation;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.laioffer.washerdrymanagement.R;
import com.laioffer.washerdrymanagement.database.DataRepository;
import com.laioffer.washerdrymanagement.database.Reservation;
import com.laioffer.washerdrymanagement.database.TemporaryType;
import com.laioffer.washerdrymanagement.databinding.ReservationElementLayoutBinding;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {
    static public List<TemporaryType> reservations = new ArrayList<>();
    static public Set<Integer> selected = new HashSet<>();
    private MyTask myTask;
    private Handler mHandler;
    public Timer timer;
    Set<ReservationViewHolder> set;
    private DataRepository repository;
    public ReservationAdapter (DataRepository repository) {
        this.repository = repository;
        myTask = new MyTask();
        timer = new Timer();
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        notifyDataSetChanged();
                        break;
                }
            }
        };
        timer.schedule(myTask, 0, 1000);

    }
    public void setReservation(List<Reservation> temp) {
        reservations.clear();
        selected.clear();
        int i = 0;
        while(i < temp.size()) {
            if (temp.get(i).condition.equals("available") || temp.get(i).condition.equals("damaged")) {
                temp.remove(i);
            }
            else {
                i++;
            }
        }
        for (Reservation reservation : temp) {
            TemporaryType newone = new TemporaryType();
            newone.address = reservation.address;
            newone.brand = reservation.brand;
            newone.condition = reservation.condition;
            newone.end_time = reservation.end_time;
            newone.item_id = reservation.item_id;
            newone.model = reservation.model;
            newone.type = reservation.type;
            processTime(newone);
            reservations.add(newone);
        }

        notifyDataSetChanged();

    }
    public void processTime(TemporaryType newone) {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        String te = t.toString();
        String[] array = te.split("\\.");
        if (array[0].length() < 19) {
            Log.d("aaaa", "error split");
        }
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            startDate = dateFormat.parse(array[0]);
            endDate = dateFormat.parse(newone.end_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp startstamp = new Timestamp(startDate.getTime());
        Timestamp endstamp = new Timestamp(endDate.getTime());
        if (startstamp.equals(endstamp) || startstamp.after(endstamp)) {
            startstamp = endstamp;
        }
        newone.start_time = startstamp.toString();
    }
    public void clear() {
        reservations.clear();
    }
    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_element_layout, parent, false);
        this.repository = new DataRepository(parent.getContext());
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        TemporaryType cur = reservations.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        holder.reserveItemTextView.setText(cur.item_id);
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = dateFormat.parse(cur.start_time);
            endDate = dateFormat.parse(cur.end_time);
        } catch (ParseException e) {
        }
        if (startDate == null) {
            Log.d("aaaa", cur.start_time);
        }
        Timestamp startstamp = new Timestamp(startDate.getTime());
        Timestamp endstamp = new Timestamp(endDate.getTime());
        long t = endstamp.getTime() - startstamp.getTime();
        long min = t / (1000 * 60);
        long sec = t / 1000 - min * 60;
        holder.reserveItemTextView.setText(cur.item_id);
        holder.reserveTextView.setText(cur.condition);
        holder.reservationTextView.setText(min + " min,  " + sec + " sec");
        if (selected.contains(position)) {
            holder.cardView.setBackgroundColor(0x0fff0000);
        }
        else {
            holder.cardView.setBackgroundColor(0xffffffff);
        }
        if (startstamp.equals(endstamp) || startstamp.after(endstamp)) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!selected.contains(position)) {
                        holder.cardView.setBackgroundColor(0x0fff0000);
                        selected.add(position);
                    }
                    else {
                        holder.cardView.setBackgroundColor(0xffffffff);
                        selected.remove(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int i = 0;

        return reservations.size();
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        ImageView reservationImageView;
        TextView reservationTextView;
        TextView reserverTextView;
        TextView reserveTextView;
        TextView reserveItemTextView;
        ConstraintLayout cardView;
        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            ReservationElementLayoutBinding binding = ReservationElementLayoutBinding.bind(itemView);
            reservationImageView =binding.reservationCardImage;
            reservationTextView = binding.reservationCardName;
            cardView = binding.selectableReservationCard;
            reserverTextView = binding.reserverText;
            reserveItemTextView = binding.reserveItemText;
            reserveTextView = binding.reserveText;
            reservationImageView.setImageResource(R.drawable.ic_event_24px);
        }
    }
    class MyTask extends TimerTask {
        @Override
        public void run() {
            if (reservations == null || reservations.isEmpty()) {
                return;
            }
            int size = reservations.size();
            for (int i = 0; i < size; i++) {
                TemporaryType temp = reservations.get(i);
                processTime(temp);
                Date startDate = null;
                Date endDate = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    startDate = dateFormat.parse(temp.start_time);
                    endDate = dateFormat.parse(temp.end_time);
                } catch (ParseException e) {
                }
                if (startDate == null) {
                    Log.d("aaaa", temp.start_time);
                }
                Timestamp startstamp = new Timestamp(startDate.getTime());
                Timestamp endstamp = new Timestamp(endDate.getTime());
                if ((startstamp.after(endstamp) || startstamp.equals(endstamp))&& temp.condition.equals("reserve")) {
                    repository.finishReservaiton(temp.item_id);
                    temp.condition = "finished";
                }
                reservations.set(i, temp);
            }
            Message message = mHandler.obtainMessage(1);
            message.arg1 = 1;
            mHandler.sendMessage(message);
        }
    }

}
