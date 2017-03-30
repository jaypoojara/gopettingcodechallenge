package com.gopettingcodingchallenge.api;

import com.gopettingcodingchallenge.model.Guide;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jaypoojara on 15-02-2017.
 */

public interface ApiInterface {

    @GET("v2/upcomingGuides")
    Call<Guide> getGuides();

}
