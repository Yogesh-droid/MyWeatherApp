package com.example.myweatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweatherapp.ViewModel.LocationViewModel;
import com.example.myweatherapp.models.Main;
import com.example.myweatherapp.models.MyData;
import com.example.myweatherapp.models.Sys;
import com.example.myweatherapp.models.Weather;
import com.example.myweatherapp.services.Client;
import com.example.myweatherapp.services.Permissions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView textView,textView2,textView3,textView4;
    FusedLocationProviderClient fusedLocationProviderClient;
    Permissions permissions;
    Client client;
    double lat,lon;
    String key="512636872197e32d30341f8d2997ffa4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);
        client=new Client();
        permissions=new Permissions(this);
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();

        Call<MyData>call=client.getClient().getData(key,lat,lon);
        call.enqueue(new Callback<MyData>() {
            @Override
            public void onResponse(Call<MyData> call, Response<MyData> response) {
                List<Weather> list=response.body().getWeather();
                Log.d("description",list.get(0).getDescription());
                Main main=response.body().getMain();
                Sys sys=response.body().getSys();
                String name=response.body().getName();
                textView.setText(list.get(0).getDescription());
                textView2.setText(main.getTemp()+"");
                textView3.setText(sys.getSunrise()+"");
                textView4.setText(name);
            }

            @Override
            public void onFailure(Call<MyData> call, Throwable t) {

            }
        });
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
                       lat=location.getLatitude();
                       Log.d("Latitude",lat+"");
                       lon=location.getLongitude();
                       Log.d("Longitude",lon+"");
                    }
                }
            });
            }else {
                Toast.makeText(this,"Please Enable Location",Toast.LENGTH_LONG).show();
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
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {

        @SuppressLint("SetTextI18n")
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lat=mLastLocation.getLatitude();
            lon=mLastLocation.getLongitude();
        }
    };

    private boolean isLocationEnabled() {
        LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                ||  locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}