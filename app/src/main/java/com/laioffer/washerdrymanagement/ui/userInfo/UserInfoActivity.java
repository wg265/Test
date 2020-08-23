package com.laioffer.washerdrymanagement.ui.userInfo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.laioffer.washerdrymanagement.Network.BackendClient;
import com.laioffer.washerdrymanagement.Network.DataApi;
import com.laioffer.washerdrymanagement.R;
import com.laioffer.washerdrymanagement.database.BackGround;
import com.laioffer.washerdrymanagement.ui.editProfile.PersonalFileActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {
    TextView firstname;
    TextView lastname;
    TextView email;
    TextView aboutme;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_userinfo);
        firstname = findViewById(R.id.first_name_text);
        lastname = findViewById(R.id.last_name_text);
        email = findViewById(R.id.email_text);
        aboutme = findViewById(R.id.about_me_text);
        Button back = findViewById(R.id.go_back);
        Button edit = findViewById(R.id.go_to_edit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserInfoActivity.this, PersonalFileActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("aaaa","onResume");
        DataApi api = BackendClient.newInstance(this).create(DataApi.class);
        api.getbackground().enqueue(new Callback<BackGround>() {
            @Override
            public void onResponse(Call<BackGround> call, Response<BackGround> response) {
                if (response.isSuccessful()) {
                    firstname.setText(response.body().first_name);
                    lastname.setText(response.body().last_name);
                    email.setText(response.body().email);
                    aboutme.setText(response.body().about_me);
                    Log.d("aaaa", "background successful");
                }
                else {
                    Log.d("aaaa", response.toString());
                }
            }

            @Override
            public void onFailure(Call<BackGround> call, Throwable t) {
                firstname.setText("");
                lastname.setText("");
                email.setText("");
                aboutme.setText("");
                Log.d("aaaa", t.toString());
            }
        });

    }
}
