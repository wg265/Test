package com.laioffer.washerdrymanagement.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;

import com.google.android.material.navigation.NavigationView;
import com.laioffer.washerdrymanagement.Network.BackendClient;
import com.laioffer.washerdrymanagement.Network.DataApi;
import com.laioffer.washerdrymanagement.R;
import com.laioffer.washerdrymanagement.database.Reservation;
import com.laioffer.washerdrymanagement.ui.detail.ReportActivity;
import com.laioffer.washerdrymanagement.ui.editProfile.PersonalFileActivity;
import com.laioffer.washerdrymanagement.ui.reservation.ReservationFragment;
import com.laioffer.washerdrymanagement.ui.reservation.TimerService;
import com.laioffer.washerdrymanagement.ui.userInfo.UserInfoActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ExpandableListAdapter adapter;
    private List<String> lstTitle;
    private Map<String, List<String>> lstChild;
    private ExpandableListView expandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        startService(new Intent(this, TimerService.class));
        setContentView(R.layout.activity_home);
        HomeActivity pointer = this;
        Toolbar headbar = findViewById(R.id.head_bar);
        setSupportActionBar(headbar);
        drawerLayout = findViewById(R.id.washer_layout);
        LinearLayout v = findViewById(R.id.side_container);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        ViewGroup.LayoutParams leftParams = v.getLayoutParams();
        leftParams.height = (int)(metric.heightPixels);
        leftParams.width = (int)(0.6 * metric.widthPixels);
        v.setLayoutParams(leftParams);
        drawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                    }
                    @Override
                    public void onDrawerOpened(@NonNull View drawerView) {
                        final TextView user_textview = (TextView) drawerView.findViewById(R.id.header_text);
                        user_textview.setText("Hi, ");
                        ImageView figure = drawerView.findViewById(R.id.figure);
                        figure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(pointer, UserInfoActivity.class));
                            }
                        });
                        TextView reserveText = (TextView) drawerView.findViewById(R.id.reservation);
                        reserveText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawerLayout.closeDrawers();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                try {
                                    Fragment fragment = (Fragment)ReservationFragment.class.newInstance();
                                    fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        TextView reportText = drawerView.findViewById(R.id.report);
                        reportText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(HomeActivity.this, ReportActivity.class));
                                drawerLayout.closeDrawers();
                            }
                        });
                        TextView logoutText = drawerView.findViewById(R.id.logout);
                        logoutText.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                DataApi dataApi = BackendClient.newInstance(HomeActivity.this).create(DataApi.class);
                                dataApi.logout().enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        Toast toast = Toast.makeText(pointer, "Logout success", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                        toast.show();
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast toast = Toast.makeText(pointer, "Could not send logout request, try again", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                        toast.show();
                                    }
                                });
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onDrawerClosed(@NonNull View drawerView) {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {

                    }
                }
        );
        genData();
        expandableListView = findViewById(R.id.nav_list);
        expandableListView.setDividerHeight(2);
        adapter = new ExpandableListAdapter(this, lstTitle, lstChild);
        expandableListView.setAdapter(adapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String selectedItem = ((List)(lstChild.get(lstTitle.get(groupPosition))))
                        .get(childPosition).toString();
                drawerLayout.closeDrawers();
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = null;
                int tempstate = 0;
                switch (selectedItem) {
                    case "All":
                        tempstate = 0;
                        break;
                    case "Available":
                        tempstate = 1;
                        break;
                    case "Reserved":
                        tempstate = 2;
                        break;
                    case"Finished":
                        tempstate = 3;
                        break;
                    case "Damaged":
                        tempstate = 4;
                        break;
                    default:
                        tempstate = 0;
                        break;
                }
                try {
                    HomeFragment.state_filter = tempstate;
                    fragment = (Fragment) HomeFragment.class.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("aaaa", "tempstate:"+tempstate);
                Log.d("aaaa", "sub home");
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                return false;
            }
        });


    }
    private void genData() {
        List<String> title = Collections.singletonList("Machines");
        List<String> childitem = Arrays.asList("All", "Available","Reserved", "Finished", "Damaged");
        lstChild = new TreeMap<>();
        lstChild.put(title.get(0), childitem);
        lstTitle = new ArrayList<>(lstChild.keySet());
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
