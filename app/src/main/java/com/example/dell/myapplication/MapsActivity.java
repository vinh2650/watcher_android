package com.example.dell.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.dell.myapplication.Class.sendLatLng;
import com.example.dell.myapplication.utils.PermissionUtils;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity
        implements
        OnMapReadyCallback,
        OnMyLocationButtonClickListener {


    Button testbtn;
    private GoogleMap mMap;
    View mapView;
    private GoogleApiClient client;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    double lat = 10.818669;
    double lng = 106.628132;
    DatabaseReference mData;
    PolylineOptions polylineOptions = new PolylineOptions()
            .geodesic(true)
            .color(Color.BLUE);
    private List<Polyline> polylinePaths = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapView = mapFragment.getView();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mData = FirebaseDatabase.getInstance().getReference();

        // button add new Latlng
        testbtn = (Button)findViewById(R.id.testbtn);
        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lat += 0.00103101;
                lng += 0.00012;
                writeNewLatLng("abc", lat, lng);
                mData.child("abc").addValueEventListener(postListener);
            }
        });
    }

    /**
     * function write new LatLng to firebase DB
     * @param ID name of child
     * @param lat
     * @param lng
     */
    private void writeNewLatLng(String ID, double lat, double lng) {
        sendLatLng latLong = new sendLatLng(lat, lng);
        mData.child(ID).setValue(latLong);
    }

    /**
     *  fuction read value on firebase DB
     */
    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            sendLatLng ll = dataSnapshot.getValue(sendLatLng.class);
            LatLng userLocation = new LatLng(ll.lat, ll.lng);
            polylineOptions.add(userLocation);
            polylinePaths.add(mMap.addPolyline(polylineOptions));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w("loadPost:onCancelled", databaseError.toException());
        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // enable myLocation button
        enableMyLocation();

        // Change position of myLocation button
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 20, 120);
        }

        mMap.setOnMyLocationButtonClickListener(this);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        LatLng userLocation = getMyLocation();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
        return false;
    }

    /**
     * get myLocation
     * @return userLocation
     */
    private LatLng getMyLocation()
    {
        LatLng userLocation = new LatLng(0, 0);
        if (ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MapsActivity.this,new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, 1);
        }
        else{
            if(!mMap.isMyLocationEnabled())
                mMap.setMyLocationEnabled(true);

            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (myLocation == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                String provider = lm.getBestProvider(criteria, true);
                myLocation = lm.getLastKnownLocation(provider);
            }

            if (myLocation!=null){
                userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            }
        }
        return userLocation;
    }

    /**
     * function enable myLocation after check
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Maps Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.dell.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onStop() {
        super.onStop();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Maps Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.dell.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
