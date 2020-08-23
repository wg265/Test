package com.laioffer.washerdrymanagement.ui.editProfile;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.laioffer.washerdrymanagement.Network.BackendClient;
import com.laioffer.washerdrymanagement.Network.DataApi;
import com.laioffer.washerdrymanagement.R;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalFileActivity extends AppCompatActivity {
    private EditText first_name;
    private EditText last_name;
    private EditText email;
    private EditText about_me;
    private EditText phone_number;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);
        Button cancel = findViewById(R.id.cancel_edit);
        first_name = findViewById(R.id.first_name_edit);
        last_name = findViewById(R.id.last_name_edit);
        email = findViewById(R.id.email_edit);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button save = findViewById(R.id.save_edit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("first_name", first_name.getText());
                    obj.put("last_name", last_name.getText());
                    obj.put("email", email.getText());
                    obj.put("phone_number", phone_number.getText());
                    obj.put("about_me", about_me.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), obj.toString());
                DataApi api = BackendClient.newInstance(PersonalFileActivity.this).create(DataApi.class);
                api.editfile(body).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast toast = Toast.makeText(PersonalFileActivity.this, "Edit Success", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                            Log.d("aaaa", response.toString());
                        }
                        else {
                            Toast toast = Toast.makeText(PersonalFileActivity.this, "Edit Failed", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                            Log.d("aaaa", response.toString());
                        }
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("aaaa", t.toString());

                    }
                });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupUI(findViewById(R.id.personal_file));
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(PersonalFileActivity.this);
                    return false;
                }
            });
        }
    }

}
