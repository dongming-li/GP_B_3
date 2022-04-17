package com.example.sulli.serverconnectiondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONException;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final TextView textToShow = (TextView) findViewById(R.id.text);
        RequestQueue request_queue = Volley.newRequestQueue(this);

        final String url ="http://proj-309-gp-b-3.cs.iastate.edu/add_user.cgi";



        StringRequest string_request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //displays the
                        textToShow.setText("Response from " + url + "\n :" + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textToShow.setText("Oops there appears to be a problem!");
            }
        });
        request_queue.add(string_request);
    }

    public void send_data()
    {
        String post_URL = "";//currently empty will be where is send the data


    }
}
