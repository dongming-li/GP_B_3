package com.example.jffalat.poolparty.Rider;
/**
 * By Justin Falat
 *
 * a class that populates a LinearLayout for the AdminHub. Contains two buttons for adding an account and viewing all created accounts
 */


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.content.Intent;

import com.example.jffalat.poolparty.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DisplayRidesActivity extends AppCompatActivity {

        private TextView rideText;
        private String time, start_address,end_address, driver, requested_time;
        JSONObject ride;

    /**
     * the method used to create the activity, where the layout is generated
     * @param savedInstanceState
     */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_displayrides);


            rideText = (TextView) findViewById(R.id.Rides_view);
            requested_time = getIntent().getStringExtra("requestedTime");
            try {
                ride = new JSONObject(getIntent().getStringExtra("Ride"));
                    time = ride.getString("Ride_Time");
                    start_address = ride.getString("Start_Address");
                    end_address = ride.getString("End_Address");
                    driver = ride.getString("Driver_Name");
                    //requested_time = getIntent().getStringExtra("requestedTime");
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            if(requested_time.equals("17.75")) {
                rideText.setText("Ride 1:\n" + "Time: " + time + "\nStart Address: " + start_address + "\nEnd Address: " + end_address + "\nDriver: " + driver);
            }
            else {
                rideText.setText("No Rides Found Matching Your Needs");
            }
            //Will eventually be a JSON Object Array and will print each json object as a separate ride in a listLayout


        }
    }

