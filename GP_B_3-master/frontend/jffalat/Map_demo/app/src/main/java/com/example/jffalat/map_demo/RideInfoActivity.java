package com.example.jffalat.map_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jffalat on 12/3/2017.
 */

public class RideInfoActivity extends Activity {

    Button btnUpdateAccounts;
    Button btnDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_info);

        Intent intent = getIntent();

        TextView temp = (TextView) findViewById(R.id.ride_name);
        temp.setText(intent.getStringExtra("rideName"));

        temp = (TextView) findViewById(R.id.ride_info);
        temp.setText(intent.getStringExtra("LatLng"));

      /*  temp = (TextView) findViewById(R.id.email);
        temp.setText("Email: " + account.getEmail());

        temp = (TextView) findViewById(R.id.password);
        temp.setText("Password: " + account.getPassword());

        temp = (TextView) findViewById(R.id.driver);
        temp.setText("Driver: " + account.getDriver());

        temp = (TextView) findViewById(R.id.rider);
        temp.setText("Rider: " + account.getRider()); */


        btnUpdateAccounts = (Button) findViewById(R.id.update_account_btn);
        btnUpdateAccounts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }

        });


    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



}
