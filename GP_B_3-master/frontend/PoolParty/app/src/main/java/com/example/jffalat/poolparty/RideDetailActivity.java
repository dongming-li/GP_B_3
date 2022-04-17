package com.example.jffalat.poolparty;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Desplays details on a pending ride as well as the other riders and driver and buttons that send to the driver/riders specific chat
 */
public class RideDetailActivity extends AppCompatActivity{
    private Ride ride_data;
    private View driver;
    private Button leave_ride_btn;
    private ArrayList<AccountData> riders_array = new ArrayList<>();
    private AccountData user_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        SharedPreferences user_pref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        user_data = new AccountData(user_pref.getString("USER_ID", ""),user_pref.getString("EMAIL", ""));

        //the following code is used to retrieve data from getExtras to identify the other user the client wishes to message
        if(bundle != null) {

            ride_data = (Ride)intent.getParcelableExtra("rideData");
            Log.d("ridedetailactivity",intent.getParcelableExtra("rideData").toString());

        }
        else{
            Log.d("ridedetailactivity","bundle is null");
        }
        //the code below sends is used to actually get input and call send_message if the message is valad
        fill_UI();
    }

    /**
     * requests to remove the user from the pending ride. Calls: http://proj-309-gp-b-3.cs.iastate.edu/remove_rider.php
     */
    private void request_removal(){
        Toast.makeText(getApplicationContext(), "request_removal() called", Toast.LENGTH_LONG).show();


        RequestQueue request_queue = Volley.newRequestQueue(this);

        //temp. change to post
        StringRequest post_request = new StringRequest(Request.Method.POST, "http://proj-309-gp-b-3.cs.iastate.edu/remove_rider.php",//TODO Steves url goes here
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responsetag", response);//for testing purposes


                        Intent i = new Intent(RideDetailActivity.this, RidesListActivity.class);
                        startActivity(i);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("tag", "error");///for testing
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();


                params.put("ride_id",""+ride_data.get_ride_id());
                params.put("user_id",user_data.get_id());
                params.put("email",user_data.get_email());

                Log.d("messagerequestparam", params.toString());
                return params;
            }

        };

        request_queue.add(post_request);

    }

    /**
     * fills, generates, and populates the layout with the user data
     */
    private void fill_UI()
    {
        leave_ride_btn = (Button) findViewById(R.id.leave_ride_btn);
        leave_ride_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                request_removal();
            }

        });

        fill_driver();

        TextView date_txt = (TextView)findViewById(R.id.date);
        date_txt.setText(ride_data.get_start_time());
        TextView start_location_txt = (TextView)findViewById(R.id.start_address);
        start_location_txt.setText(ride_data.get_start_address());
        TextView end_location_txt = (TextView)findViewById(R.id.end_address);
        end_location_txt.setText(ride_data.get_end_address());
        //TextView capacity_txt = (TextView)findViewById(R.id.av_seats);
        //capacity_txt.setText(ride_data.get_total_capacity());
        //ListPassengerAdapter adapter = new ListPassengerAdapter(RideDetailActivity.this, R.layout.user_data_layout, ride_data.get_riders());
        //setListAdapter(adapter);

        //--Working on the following code hesitantly--


        LinearLayout riders = (LinearLayout)findViewById(R.id.riders);
        ListPassengerAdapter adapter = new ListPassengerAdapter(RideDetailActivity.this, R.layout.user_data_layout, ride_data.get_riders());
        //setListAdapter(adapter);
        final int adapterCount = adapter.getCount();

        for (int i = 0; i < adapterCount; i++) {
            View item = adapter.getView(i, null, null);
            riders.addView(item);
        }

    }

    /**
     * fills the XML with the data of the driver
     */
    private void fill_driver(){
        driver = (LinearLayout)findViewById(R.id.driver);//TODO Finish implementing this method
        TextView email = (TextView)driver.findViewById(R.id.email);
        email.setText(ride_data.get_driver().get_email());
        TextView id = (TextView)driver.findViewById(R.id.user_id);
        id.setText(ride_data.get_driver().get_id());
        TextView rating = (TextView)driver.findViewById(R.id.rating);
        rating.setText(ride_data.get_driver().get_rating());
        Button message_btn = (Button)driver.findViewById(R.id.message_btn);
        message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RideDetailActivity.this, ChatActivity.class);
                i.putExtra("recipient_email",ride_data.get_driver().get_email());
                i.putExtra("recipient_id",ride_data.get_driver().get_id());
                startActivity(i);
            }
        });
    }
}
