package com.example.myweatherapp.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    public Api getClient(){
        Retrofit retrofit=new Retrofit.Builder().
                baseUrl(Api.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        Api api=retrofit.create(Api.class);
        return api;
    }
}
