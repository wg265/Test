package com.laioffer.washerdrymanagement.remote;

import androidx.lifecycle.LiveData;

import com.laioffer.washerdrymanagement.remote.response.RemoteResponse;

public interface RemoteResponseListener<T> {
    void onStart();
    void onSuccess(LiveData<RemoteResponse<T>> response);
    void onFailure(String msg);
}
