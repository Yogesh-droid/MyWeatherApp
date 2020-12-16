package com.example.myweatherapp.services;

import com.example.myweatherapp.models.MyData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL="https://api.openweathermap.org/data/2.5/";


    @GET("weather")
    Call<MyData> getData(@Query("appid")String key,@Query("lat")double latitude,@Query("lon")double longitude);
}
