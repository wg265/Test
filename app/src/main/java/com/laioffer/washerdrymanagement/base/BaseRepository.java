package com.laioffer.washerdrymanagement.base;

import com.laioffer.washerdrymanagement.remote.ApiService;
import com.laioffer.washerdrymanagement.remote.ApiUtils;

public abstract class BaseRepository {
    protected final ApiService apiService = ApiUtils.getAPIService();

}