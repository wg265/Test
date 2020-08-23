package com.laioffer.washerdrymanagement.database;
import com.laioffer.washerdrymanagement.Network.*;
//import com.laioffer.washerdrymanagement.ui.home.HomeApplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
    private Context context;
    private DataApi dataApi;
//    private final ReservationDatabase database;
    public DataRepository(Context context){
        this.context = context;
        this.dataApi = BackendClient.newInstance(context).create(DataApi.class);
//        database = ((HomeApplication) context.getApplicationContext()).getDatabase();
    }
    public LiveData<List<Item>> getWashers() {
        MutableLiveData<List<Item>> washerMutableLiveData = new MutableLiveData<>();
        dataApi.getwasher().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                    Log.d("aaaa", response.toString());
                    List<Item> res = response.body();
                    washerMutableLiveData.setValue(res);

            }
            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.d("aaaa", t.toString());
                washerMutableLiveData.setValue(new ArrayList<Item>());
            }
        });
        List<Item> temp = washerMutableLiveData.getValue();
        return washerMutableLiveData;
    }
    public LiveData<List<Reservation>> getReservations() {
        MutableLiveData<List<Reservation>> resultLiveData = new MutableLiveData<>();
        dataApi.getReservations().enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                Log.d("aaaa", "reservation success");
                List<Reservation> res = response.body();
                if (res != null && res.size() != 0)
                resultLiveData.setValue(res);
                else {
                    resultLiveData.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Log.d("aaaa", t.toString());
                resultLiveData.setValue(new ArrayList<>());
            }
        });
        return resultLiveData;
    }
    public void deleteReservaiton(String item_id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", "available");
            jsonObject.put("item_id", item_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("Content-Type, application/json"), jsonObject.toString());
        dataApi.deleteReservation(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast toast = Toast.makeText(context, "delete Success", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
                else {
                    Log.d("aaaa", response.message());
                    Toast toast = Toast.makeText(context, "delete failed", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast toast = Toast.makeText(context, "cannot connect method", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });
    }


}
