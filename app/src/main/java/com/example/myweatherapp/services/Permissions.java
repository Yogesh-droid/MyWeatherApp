package com.example.myweatherapp.services;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class Permissions {
    Context context;
    int PERMISSION_ID = 44;

    public Permissions(Context context) {
        this.context = context;
    }

    public boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION )==
                PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION)==
                        PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissions() {
        ActivityCompat.requestPermissions((Activity) context,new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_ID);
    }
}
