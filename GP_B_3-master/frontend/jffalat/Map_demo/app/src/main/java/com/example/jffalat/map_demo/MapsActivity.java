package com.example.jffalat.map_demo;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        // TODO Add a marker in your hometown
        LatLng Carpentersville = new LatLng(42.1218560, -88.3224990);
        mMap.addMarker(new MarkerOptions().position(Carpentersville).title("Home"));
        //newMarker(Carpentersville);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.131817, -88.329415))
                .title("Ride 1")
                .snippet("and snippet")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        // TODO Move the camera to the Marker Location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Carpentersville, 15));
        // TODO Change the initial states of the Map.
        drawCircle(Carpentersville);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent1 = new Intent(MapsActivity.this, RideInfoActivity.class);
                String title = marker.getTitle();
                LatLng pos = marker.getPosition();
                intent1.putExtra("rideName", title);
                intent1.putExtra("LatLng", pos);
                startActivity(intent1);
            }
        });

    }

    private void newMarker(LatLng point) {
        mMap.addMarker(new MarkerOptions()
                .position(point)
                .title("Ride 1")
                .snippet("and snippet")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    }

    private void drawCircle(LatLng point){
/*
        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();

        // Specifying the center of the circle
        circleOptions.center(point);

        // Radius of the circle
        circleOptions.radius(600);

        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK);

        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000);

        // Border width of the circle
        circleOptions.strokeWidth(2);

        // Adding the circle to the GoogleMap
        mMap.addCircle(circleOptions);
*/

        mMap.addCircle(new CircleOptions()
                .center(point)
                .radius(805)
                .strokeColor(Color.BLACK)
                .strokeWidth(2)
                .fillColor(0x30ff0000));
    }
}
