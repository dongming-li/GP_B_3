package com.example.jffalat.poolparty.Rider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jffalat.poolparty.AccountData;
import com.example.jffalat.poolparty.R;
import com.example.jffalat.poolparty.Ride;
import com.example.jffalat.poolparty.RideDetailActivity;
import com.example.jffalat.poolparty.RidesListActivity;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * a class that populates a LinearLayout that contains the ride info
 *
 */

public class RideInfoActivity extends Activity {

    private AccountData user_data;
    protected Button btnUpdateAccounts;
    protected Button btnDeleteAccount;
    private Ride ride_data;//This doen't need to be a Ride object (it could just be a string of its id) but it would probably be preferred to use the Ride object
    Button btnJoinRide;

    /**
     * Called to create the Activity
     * @param savedInstanceState the instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_info);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            ride_data = (Ride)intent.getParcelableExtra("rideData");
            Log.d("ridedetailactivity",intent.getParcelableExtra("rideData").toString());


        }
        else{
            Log.d("ridedetailactivity","bundle is null");
        }

        SharedPreferences user_pref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        user_data = new AccountData(user_pref.getString("USER_ID", ""),user_pref.getString("EMAIL", ""));

        TextView temp = (TextView) findViewById(R.id.ride_name);
        temp.setText(intent.getStringExtra("rideName"));

        temp = (TextView) findViewById(R.id.ride_info);
        temp.setText(intent.getStringExtra("info"));

      /*  temp = (TextView) findViewById(R.id.email);
        temp.setText("Email: " + account.getEmail());

        temp = (TextView) findViewById(R.id.password);
        temp.setText("Password: " + account.getPassword());

        temp = (TextView) findViewById(R.id.driver);
        temp.setText("Driver: " + account.getDriver());

        temp = (TextView) findViewById(R.id.rider);
        temp.setText("Rider: " + account.getRider()); */


        btnJoinRide = (Button) findViewById(R.id.join_ride_btn);
        btnJoinRide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                join_request();
               /* Intent i = new Intent (RideInfoActivity.this, RiderHubActivity.class);
                startActivity(i);*/
            }

        });


    }

    /**
     * onResume method that calls super.onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * onPause method that calls super.onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Method to make POST request to the server.
     *
     */
    private void join_request(){

        RequestQueue request_queue = Volley.newRequestQueue(this);

        //temp. change to post
        StringRequest post_request = new StringRequest(Request.Method.POST, "http://proj-309-gp-b-3.cs.iastate.edu/add_rider.php",//TODO Steves url goes here
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responsetag", response);//for testing purposes


                        Intent i = new Intent(RideInfoActivity.this, RiderHubActivity.class);
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

}
