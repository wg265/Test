package com.laioffer.washerdrymanagement.ui.detail;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.laioffer.washerdrymanagement.R;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        int timeL = 50; // get from api
        String fullstr = (timeL) + "mins";
        setContentView(R.layout.fragment_detail);
        TextView timeleft;
        timeleft = findViewById(R.id.details_time_left);
        timeleft.setText(fullstr);
    }

    public void terminateWasher(View V){
        // send terminate signal
    }
    public void GotoReport(View V){
        //navigate to report fragment
    }

}