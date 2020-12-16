package com.example.myweatherapp.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LocationServices {
    Permissions permissions;
    FusedLocationProviderClient fusedLocationProviderClient;
    Context context;

    public LocationServices(Context context,FusedLocationProviderClient fusedLocationProviderClient) {
        this.context = context;
        this.fusedLocationProviderClient=fusedLocationProviderClient;
        permissions=new Permissions(context);
    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if(permissions.checkPermissions()){
            if(isLocationEnabled()){
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location=task.getResult();
                        if(location==null){
                            requestNewLocationData();
                        }else {

                        }
                    }
                });
            }else {

            }

        }else {
            permissions.requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        fusedLocationProviderClient = com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(context);

        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }
    private LocationCallback mLocationCallback = new LocationCallback() {

        @SuppressLint("SetTextI18n")
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
        }
    };

    private boolean isLocationEnabled() {
        LocationManager locationManager=(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                ||  locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}
