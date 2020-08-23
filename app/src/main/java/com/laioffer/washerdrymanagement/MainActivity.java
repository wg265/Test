package com.laioffer.washerdrymanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.laioffer.washerdrymanagement.Notification.CHANNEL_ID;
import static com.laioffer.washerdrymanagement.Notification.CHANNEL_LEFTID;
import com.laioffer.washerdrymanagement.ui.NavigationManager;

public class MainActivity extends AppCompatActivity implements NavigationManager {
    private NotificationManagerCompat notificationManager;
    private TextView timeleft;
    private TextView machineID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        navigateTo(new OnBoardingBaseFragment());
    }

    @Override
    public void navigateTo(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.first_fragment, fragment, null)
                .addToBackStack(null)
                .commit();
    }
    public void sendOnChannelmachine(){
        String title = machineID.getText().toString();
        String message = "take the cloth out!";
        android.app.Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_takeitout)
                .setContentTitle(title)
                .setContentText(message)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .build();
        notificationManager.notify(1, notification);

    }
    public void sendOnChannelleft(){
        String title = "3 mins left!";
        String message = timeleft.getText().toString();
        android.app.Notification notification = new NotificationCompat.Builder(this, CHANNEL_LEFTID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle(title)
                .setContentText(message)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .build();
        notificationManager.notify(2, notification);
    }

    // add Fragment to the activity
    //getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, MainFragment.newInstance()).commit();
}

