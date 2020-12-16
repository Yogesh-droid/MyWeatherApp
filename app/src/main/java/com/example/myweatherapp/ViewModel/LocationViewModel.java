package com.example.myweatherapp.ViewModel;

import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {
    private String latitude,longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
