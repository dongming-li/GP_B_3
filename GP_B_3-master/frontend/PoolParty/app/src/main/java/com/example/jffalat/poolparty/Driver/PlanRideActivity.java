package com.example.jffalat.poolparty.Driver;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONException;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jffalat.poolparty.AccountData;
import com.example.jffalat.poolparty.R;

import java.util.HashMap;
import java.util.Map;
/**
 * class that populates a LinearLayout that contains EditTexts for input from driver to plan a ride
 */


public class PlanRideActivity extends AppCompatActivity {

    private Button btnPlanARide;
    private EditText time_text;
    private EditText destination_address_text;
    private EditText start_address_text;
    private EditText seats_offered;
    private EditText max_distance_txt;
    private String time, start_address, destination_address, number_of_seats, max_distance;

    private JSONObject rideJSON = new JSONObject();
    //private String time, destination_address, start_address, driver_email;
    //private String number_seats;

    //JSONObject planned_ride = new JSONObject();
    private AccountData user_data;

    /**
     * the method used to create the activity, where the layout is generated
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planride);


        SharedPreferences user_pref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        user_data = new AccountData(user_pref.getString("USER_ID", ""),user_pref.getString("EMAIL", ""));

        time_text = (EditText) findViewById(R.id.Time);
        start_address_text = (EditText) findViewById(R.id.Start_Location_txt);
        destination_address_text = (EditText) findViewById(R.id.Destination_txt);
        seats_offered = (EditText) findViewById(R.id.Seats_offered);
        max_distance_txt = (EditText) findViewById(R.id.max_radius);

        btnPlanARide = (Button) findViewById(R.id.Plan_Ride_btn);
        btnPlanARide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                time = time_text.getText().toString();
                start_address = start_address_text.getText().toString();
                destination_address = destination_address_text.getText().toString();
                number_of_seats = seats_offered.getText().toString();
                max_distance = max_distance_txt.getText().toString();

               /* Toast.makeText(getApplicationContext(), time, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), start_address, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), destination_address, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), number_of_seats, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), max_distance, Toast.LENGTH_LONG).show(); */

                if (acceptable_field(time, time_text) && acceptable_field(start_address, start_address_text) && acceptable_field(destination_address, destination_address_text) && acceptable_field(number_of_seats,seats_offered))
                {
                    try {
                        rideJSON.put("time", time);
                        rideJSON.put("start_address", start_address);
                        rideJSON.put("max_distance",max_distance_txt);
                        rideJSON.put("destination_address", destination_address);
                        rideJSON.put("number_of_seats", number_of_seats);


                        if (send_data(rideJSON)) {
                            Log.d("myTag", "success");
                            //figure out how to determine if the data was successfully added to the server
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }



            }

        });

    }

    /**
     * This method checks that the user's input is valid before sending to the server
     * @param str String vairable of input from user
     * @param field EditText field that user inputs the string into
     * @return True if input is acceptable, False if not
     */
    private boolean acceptable_field(String str, EditText field)
    {
        if (!str.equals("")) {

                return true;

        } else
        {
            field.setText("");
            field.setHint("Please Enter field");
        }
        return false;
    }

    /**
     * Method to make POST request to the server. Sends account info from user input as a dictionary.
     * @param json JSONObject that stores user input to pass as a single parameter
     * @return
     */
    private boolean send_data(JSONObject json)
    {
        String url ="http://proj-309-gp-b-3.cs.iastate.edu/create_ride.php";//TODO use correct directory


        RequestQueue request_queue = Volley.newRequestQueue(this);


        StringRequest post_request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("myTag",response);//for testing purposes
                        Log.d("myTag","success");
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("myTag","error");///for testing
                        //Toast.makeText(getApplicationContext(), "POST Error", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:


                try {

                    params.put("start_address", rideJSON.getString("start_address"));
                    params.put("end_address", rideJSON.getString("destination_address"));
                    params.put("time", rideJSON.getString("time"));
                    params.put("max_passengers", rideJSON.getString("number_of_seats"));
                    params.put("max_radius", rideJSON.getString("max_distance"));
                    params.put("user_id", "10");
                    params.put("email", "driver@test.com");
                    /*if (user_data.get_email().equals("")) {

                    } else {
                        Log.d("email is: ", user_data.get_email());
                        params.put("email", user_data.get_email());
                        Toast.makeText(getApplicationContext(), user_data.get_email(), Toast.LENGTH_LONG).show();
                    } */


                    /*if (user_data.get_id().equals("")) {

                    } else {
                        Log.d("user_id is: ", user_data.get_id());
                        params.put("user_id", user_data.get_id());
                    }*/

/*
                    params.put("start_address", "1208 N 161 Circle Omaha, NE 68118");
                    params.put("end_address", "140 Lynn Avenue Ames, IA 50014");
                    params.put("time", "00:00:00:01");
                    params.put("max_passengers", "99");
                    params.put("max_radius", "1");
                    params.put("email", "driver@test.com");
                    params.put("user_id", "10");
*/

                    return params;

               }catch(JSONException e){
                    e.printStackTrace();
                }
                return null;


            }

        };

            Log.d("POST URL", post_request.getCacheKey());

        request_queue.add(post_request);

        return false;
    }
}
