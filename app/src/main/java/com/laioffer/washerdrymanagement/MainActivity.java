package com.laioffer.washerdrymanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.laioffer.washerdrymanagement.ui.NavigationManager;

public class MainActivity extends AppCompatActivity implements NavigationManager {

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


    // add Fragment to the activity
    //getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, MainFragment.newInstance()).commit();
}

