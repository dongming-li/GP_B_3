package com.example.jffalat.poolparty;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * this activity show a list of all pending rides. Clicking on the rides will send the user to the RideDetailsActivity of that specific ride
 */
public class RidesListActivity extends ListActivity {

    private String saved_email, saved_id;
    private ArrayList<Ride> ride_array = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides_list);
        Intent intent = getIntent();


        //the following code is used to retrieve user data needed to properly run
        try {
            SharedPreferences user_email = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
            saved_email = user_email.getString("EMAIL", "");
            saved_id = user_email.getString("USER_ID", "");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        update_rideList(true);

        ListRideAdapter adapter = new ListRideAdapter(RidesListActivity.this, R.layout.ridelayoutreduced, ride_array);
        setListAdapter(adapter);
    }

    /**
     *
     * @param debug if true fills the ride array with stump values, if false, it calls the primary update_rideList method to run the method normally
     */
    private void update_rideList(boolean debug)
    {
        if(!debug){
            update_rideList("http://proj-309-gp-b-3.cs.iastate.edu/get_email_riders");
            update_rideList("http://proj-309-gp-b-3.cs.iastate.edu/get_email_drivers");
            return;
        }
        ride_array.add(new Ride(new AccountData("8675309","test@email.com"), "11", "140 lynn avenue","120 lynn avenue", 5,0));
        ride_array.add(new Ride(new AccountData("2222222","test1@email2.com"), "21", "140 notlynn avenue","120 notlynn avenue", 4,0));
        ArrayList<AccountData> riders = new ArrayList();
        riders.add(new AccountData("2222222","test1@email2.com"));
        riders.add(new AccountData("6600222","test2@email2.com"));
        riders.add(new AccountData("0000000","test3@email2.com"));
        riders.add(new AccountData("1234567","test4@email2.com"));
        ride_array.add(new Ride(new AccountData("9999999","test5@email2.com"),riders, "2", "140 lynnish avenue","120 lynnish avenue", 4,0));
    }

    /**
     * calls the server to recieve an updated list of pending rides the user is currently a rider or driver in
     * @param url the URL the request is being made to
     */
    private void update_rideList(String url)
    {
        RequestQueue request_queue = Volley.newRequestQueue(this);

        //temp. change to post
        StringRequest post_request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responsetag", response);//for testing purposes
                        try{
                            JSONArray responseJSON = new JSONArray(response);
                            for(int count = 0; count < responseJSON.length();count++)
                            {
                                ride_array.add(new Ride(responseJSON.getJSONObject(count)));
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }

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

                if (saved_email.equals("")) {
                    params.put("email", "sender@email.com");
                } else {
                    Log.d("email is: ", saved_email);
                    params.put("email", saved_email);
                }
                if (saved_id.equals("")) {
                    params.put("id", "8675309");
                } else {
                    Log.d("userID is: ", saved_id);
                    params.put("user_id", saved_id);

                }

                Log.d("messagerequestparam", params.toString());
                return params;
            }

        };

        request_queue.add(post_request);

    }

    /**
     * Starts RideDetailActivity for the specific rice being clicked
     * @param l the ListView containing the view
     * @param v the specific View being clicked
     * @param position the index of the view within the listView
     * @param id of the view
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Ride ridedata = ride_array.get(position);
        Log.d("ridedata",ridedata.toString());
        Intent i = new Intent(RidesListActivity.this, RideDetailActivity.class);
        i.putExtra("rideData",ridedata);
        startActivity(i);


    }



}
