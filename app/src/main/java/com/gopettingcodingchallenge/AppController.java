package com.gopettingcodingchallenge;

import android.app.Application;

import com.gopettingcodingchallenge.api.ApiClient;
import com.gopettingcodingchallenge.api.ApiInterface;

/**
 * Created by jaypoojara on 31-03-2017.
 */

public class AppController extends Application {
    private static AppController instance;
    private ApiInterface apiInterface;

    public static AppController getInstance() {
        return instance;
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    }
}
