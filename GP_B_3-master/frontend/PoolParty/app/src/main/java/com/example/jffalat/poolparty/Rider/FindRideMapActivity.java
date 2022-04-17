package com.example.jffalat.poolparty.Rider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jffalat.poolparty.AccountData;
import com.example.jffalat.poolparty.Ride;
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

import com.example.jffalat.poolparty.R;

import java.util.ArrayList;
import java.util.List;

/**
 * a class that populates a mapView based on input from FindRideActivity
 */

public class FindRideMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String start_address, end_address, time, seats;

    /**
     * the method used to create the activity, where the layout is generated
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        start_address = intent.getStringExtra("start");
        end_address = intent.getStringExtra("end");
        time = intent.getStringExtra("time");
        seats = intent.getStringExtra("seats");

        Toast.makeText(getApplicationContext(), '"' + start_address + '"', Toast.LENGTH_LONG).show();

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



         //Test Code for Map Activity


     /*   if (start_address.equals("6214 Providence Drive Carpentersville, IL 60110"))
        {
            // TODO Add a marker in your hometown
            LatLng Carpentersville = new LatLng(42.1218560, -88.3224990);
            mMap.addMarker(new MarkerOptions().position(Carpentersville).title("Start Address"));
            //newMarker(Carpentersville);
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(42.123209, -88.319693))
                    .title("Ride 1")
                    .snippet("Driver: Joe Smith\nStart Address: 2901 Plantation Dr, Carpentersville, IL 60110, USA\nSeats: "+ seats)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(42.131817, -88.329415))
                    .title("Ride 2")
                    .snippet("Driver: Alec Taylor\nStart Address: 4402 Northgate Ct. Carpentersville, IL 60110\nSeats:  " + seats)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            // TODO Move the camera to the Marker Location
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Carpentersville, 15));
            // TODO Change the initial states of the Map.
            drawCircle(Carpentersville);
        }

        else if (start_address.equals("1671 Southridge Trail Algonquin, IL 60102"))
        {
            LatLng connorsHouse = new LatLng(42.136440, -88.318329);
            mMap.addMarker(new MarkerOptions().position(connorsHouse).title("Start Address"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(connorsHouse, 15));
            drawCircle(connorsHouse);
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(42.138678, -88.316455))
                    .title("Ride 1")
                    .snippet("Driver: Josh Long\nStart Address: 2-4 Sedgewood Ct, Algonquin, IL 60102, USA\nSeats: " + seats)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(42.136874, -88.323469))
                    .title("Ride 1")
                    .snippet("Driver: Emily Kent\nStart Address: 1841 Highmeadow Ln, Algonquin, IL 60102, USA\nSeats: " + seats)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }

        else if (start_address.equals("3800 Lincoln Way Ames, IA 50014"))
        {
            LatLng Hyvee = new LatLng(42.021628, -93.669877);
            mMap.addMarker(new MarkerOptions().position(Hyvee).title("Start Address"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Hyvee, 15));
            drawCircle(Hyvee);
            Intent intent1 = new Intent(FindRideMapActivity.this, RideInfoActivity.class);
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(42.023929, -93.668715))
                    .title("Ride 1")
                    .snippet("Driver: Adam Ross\nStart Address: 184-198 Marshall Ave, Ames, IA 50014, USA\nSeats: " + seats)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(42.020233, -93.672916))
                    .title("Ride 2")
                    .snippet("Driver: Bob Turner\nStart Address: 4025 Aplin Rd, Ames, IA 50014, USA\nSeats: " + seats)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(42.019847, -93.668824))
                    .title("Ride 3")
                    .snippet("Driver: Amanda Johnson\nStart Address: 305 Sunflower Dr, Ames, IA 50014, USA\nSeats: " + seats)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        } */


            LatLng address = getLocationFromAddress(this, start_address);
            mMap.addMarker(new MarkerOptions().position(address).title("Start Address"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(address, 15));
            drawCircle(address);




        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent1 = new Intent(FindRideMapActivity.this, RideInfoActivity.class);

                ArrayList<AccountData> riders = new ArrayList();
                riders.add(new AccountData("2222222","test1@email2.com"));
                riders.add(new AccountData("6600222","test2@email2.com"));
                riders.add(new AccountData("0000000","test3@email2.com"));
                riders.add(new AccountData("1234567","test4@email2.com"));

                String title = marker.getTitle();
                LatLng pos = marker.getPosition();
                String Details = marker.getSnippet();
                intent1.putExtra("rideName", title);
                intent1.putExtra("info", Details);
                intent1.putExtra("rideData", new Ride(new AccountData("9999999","test5@email2.com"),riders, "2", "140 lynn avenue","120 lynn avenue", 4,1234567));//TODO use a real id value from the ride
                intent1.putExtra("LatLng", pos);
                startActivity(intent1);
            }
        });

    }

    /**
     * Draws a search radius circle on map
     * @param point Starting location, center of circle
     */
    private void drawCircle(LatLng point){
        mMap.addCircle(new CircleOptions()
                .center(point)
                .radius(1610)
                .strokeColor(Color.BLACK)
                .strokeWidth(2)
                .fillColor(0x30ff0000));
    }

    /**
     * Converts Street address to latitude and longitude
     * @param context
     * @param strAddress
     * @return
     */

    public LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;

    }
}
