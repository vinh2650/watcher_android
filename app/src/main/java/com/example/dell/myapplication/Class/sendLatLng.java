package com.example.dell.myapplication.Class;

/**
 * Created by DELL on 10/21/2016.
 */
public class sendLatLng {
    public double lat;
    public double lng;

    public sendLatLng() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public sendLatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
