package com.laioffer.washerdrymanagement.remote;

public class ApiUtils {
    public static final String BASE_URL = "url";

    public static ApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
