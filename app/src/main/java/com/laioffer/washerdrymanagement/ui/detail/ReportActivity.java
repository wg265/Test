package com.laioffer.washerdrymanagement.ui.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.laioffer.washerdrymanagement.R;

public class ReportActivity extends AppCompatActivity {
    private EditText editTextMachineID;
    private EditText editTexterror;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_report);
        editTextMachineID = findViewById(R.id.report_machineID);
        editTexterror = findViewById(R.id.report_error);

    }

    public void sendReport(View V){
        String title = editTextMachineID.getText().toString();
        String errorMessage = editTexterror.getText().toString();
        //send api
    }
}