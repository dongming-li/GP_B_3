package com.example.jffalat.poolparty.Driver;

/**
 * Created by jffalat on 12/4/2017.
 *
 * a class that populates a LinearLayout for the DriverHub. Contains buttons to access driver functions
 *
 */


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jffalat.poolparty.Account;
import com.example.jffalat.poolparty.ChatActivity;
import com.example.jffalat.poolparty.MessageListener;
import com.example.jffalat.poolparty.ProfileActivity;
import com.example.jffalat.poolparty.R;
import com.example.jffalat.poolparty.Rider.FindRideActivity;
import com.example.jffalat.poolparty.RidesListActivity;
import com.example.jffalat.poolparty.UserConversationsActivity;


public class DriverHubActivity extends AppCompatActivity {

    private Button btnFindRide;
    private Button btnPlanRide;
    private Button sendMessageTEST;
    private Button btnMyRidesTEST;
    private Button btnProfile;
    private Account account;
    private Button btnConvos;

    /**
     * the method used to create the activity, where the layout is generated
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        startService(new Intent(this, MessageListener.class));
        Log.d("background service","messagelistener started (hopefully)");

        final Bundle b = getIntent().getExtras();
        account = (Account) b.getSerializable("account");

        btnPlanRide = (Button) findViewById(R.id.Plan_a_Ride_btn);
        btnPlanRide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(DriverHubActivity.this, PlanRideActivity.class);
                startActivity(i);
                //finish();
            }

        });

        btnFindRide = (Button) findViewById(R.id.Find_a_Ride_btn);
        btnFindRide.setVisibility(View.GONE);
        btnFindRide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(DriverHubActivity.this, FindRideActivity.class);
                startActivity(i);
                //finish();
            }

        });

        sendMessageTEST = (Button) findViewById(R.id.send_test_message_btn);
        sendMessageTEST.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(DriverHubActivity.this, ChatActivity.class);
                i.putExtra("recipient_email","test2@email2.com");//TODO replace these stubs with actual values rather than stubs
                i.putExtra("recipient_id","6600222");
                startActivity(i);
                //finish();
            }

        });

        btnConvos = (Button) findViewById(R.id.user_conversations);
        btnConvos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(DriverHubActivity.this, UserConversationsActivity.class);
                i.putExtra("recipient_email","sender@email.com");//TODO replace these stubs with actual values rather than stubs
                i.putExtra("recipient_id","8675309");
                startActivity(i);
                //finish();
            }

        });

        btnMyRidesTEST = (Button) findViewById(R.id.test_rides_activity);
        btnMyRidesTEST.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(DriverHubActivity.this, RidesListActivity.class);
                i.putExtra("recipient_email","recipient@email.com");//TODO replace these stubs with actual values rather than stubs
                i.putExtra("recipient_id","8675309");
                startActivity(i);
                //finish();
            }

        });

        btnProfile = (Button) findViewById(R.id.profile);
        btnProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(DriverHubActivity.this, ProfileActivity.class);
                i.putExtra("recipient_email","recipient@email.com");//TODO replace these stubs with actual values rather than stubs
                i.putExtra("recipient_id","8675309");
                i.putExtras(b);
                startActivity(i);
                finish();
            }

        });

        btnConvos = (Button) findViewById(R.id.test_rides_activity);
        btnConvos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(DriverHubActivity.this, UserConversationsActivity.class);
                i.putExtra("recipient_email","recipient@email.com");//TODO replace these stubs with actual values rather than stubs
                i.putExtra("recipient_id","8675309");
                startActivity(i);
                //finish();
            }

        });
    }

}


