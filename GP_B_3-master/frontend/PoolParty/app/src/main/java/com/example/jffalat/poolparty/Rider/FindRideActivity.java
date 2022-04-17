package com.example.jffalat.poolparty.Rider;
/**
 * class that populates a LinearLayout that contains EditTexts for input from rider to search for a ride
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jffalat.poolparty.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class FindRideActivity extends AppCompatActivity {

    private Button btnFindARide;
    private EditText time_text;
    private EditText destination_address_text;
    private EditText start_address_text;
    private EditText seats_needed;

    private String time, destination_address, start_address, driver_email;
    private String number_seats;

    JSONObject ride = new JSONObject();
    JSONObject receivedRide;


    /**
     * the method used to create the activity, where the layout is generated
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findride);

        final RequestQueue request_queue = Volley.newRequestQueue(this);
        final String url = "http://proj-309-gp-b-3.cs.iastate.edu/cgi-bin/TESTING_GET_RIDE.py";

        time_text = (EditText) findViewById(R.id.Time);
        start_address_text = (EditText) findViewById(R.id.Start_Location_txt);
        destination_address_text = (EditText) findViewById(R.id.Destination_txt);
        seats_needed = (EditText) findViewById(R.id.Seats_needed);

        btnFindARide = (Button) findViewById(R.id.Find_Ride_btn);
        btnFindARide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //TODO Check if all fields are acceptable
                time = time_text.getText().toString();
                start_address = start_address_text.getText().toString();
                destination_address = destination_address_text.getText().toString();
                number_seats = seats_needed.getText().toString();

                //TODO set values of JSON object and POST to proper cgi url
                //url = "http://proj-309-gp-b-3.cs.iastate.edu/NAME_OF_CGI_SCRIPT"

                //Toast.makeText(getApplicationContext(), start_address, Toast.LENGTH_LONG).show();


                Intent i = new Intent(FindRideActivity.this, FindRideMapActivity.class);
                i.putExtra("start", start_address);
                i.putExtra("end", destination_address);
                i.putExtra("time", time);
                i.putExtra("seats", number_seats);
                startActivity(i);

                try {
                    ride.put("time", time);
                    ride.put("start_address", start_address);
                    ride.put("destination_address", destination_address);
                    ride.put("seats_available", number_seats);
                    /*if(send_data(ride))
                    {
                        Log.d("tag","success");
                        //figure out how to determine if the data was successfully added to the server
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                JsonObjectRequest string_request = new JsonObjectRequest(Request.Method.GET, url, receivedRide,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                /*Intent i = new Intent(FindRideActivity.this, FindRideMapActivity.class);
                                i.putExtra("start_address", start_address);
                                startActivity(i); */

                                //opens DisplayRides Activity with new intent and passes json object through
                                //Intent i = new Intent(FindRideActivity.this, DisplayRidesActivity.class);
                                //receivedRide = response;
                                //Toast.makeText(getApplicationContext(), receivedRide.toString(), Toast.LENGTH_LONG).show();
                                //i.putExtra("requestedTime", time);
                                //i.putExtra("Ride", response.toString());
                                //startActivity(i);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Display Error Toast
                        Toast.makeText(getApplicationContext(), "There appears to be an error", Toast.LENGTH_LONG).show();
                    }
                });

                request_queue.add(string_request);

            }

        });
    }


    /**
     * Method to make POST request to the server. Sends account info from user input as a dictionary.
     * @param jsonObj JSONObject that stores user input to pass as a single parameter
     * @return
     */
    private boolean send_data(JSONObject jsonObj) {


        String url = "http://proj-309-gp-b-3.cs.iastate.edu/cgi-bin/get_ride_2.py";
        RequestQueue request_queue = Volley.newRequestQueue(this);

        StringRequest post_request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("tag", response);

                        Toast.makeText(getApplicationContext(), "Successful POST", Toast.LENGTH_LONG).show();

                        //TODO: Process Response, expected is a JSONArray of Ride JsonObjects
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("tag", "error");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                try {
                    params.put("time", ride.getString("time"));
                    params.put("start_address", ride.getString("start_address"));
                    params.put("end_address", ride.getString("destination_address"));
                    params.put("num_seats", ride.getString("number_seats"));
                    return params;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        request_queue.add(post_request);
        return false;

    }
}

