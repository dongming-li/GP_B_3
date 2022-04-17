package com.example.jffalat.poolparty;
/**
 * By Justin Falat
 *
 * the home-page of the application. Where the user is first sent after signing in. It contains multiple buttons that send
 * the user to other activities
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jffalat.poolparty.Driver.PlanRideActivity;
import com.example.jffalat.poolparty.Rider.FindRideActivity;


public class HubActivity extends AppCompatActivity {

    private Button btnFindRide;
    private Button btnPlanRide;
    private Button sendMessageTEST;
    private Button btnMyRidesTEST;
    private Button btnConvos;
    private Button btnProfile;
    private Account account;

    /**
     * the method used to create the activity, where the layout is generated
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        //temporary shared preferences TODO make it not stubs
        SharedPreferences.Editor user_pref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE).edit();
        user_pref.putString("USER_ID", "8675309");//TODO use actual user data not stubs
        user_pref.putString("EMAIL", "recipient@email.com");
        user_pref.apply();

        startService(new Intent(this, MessageListener.class));
        Log.d("background service","messagelistener started (hopefully)");

        final Bundle b = getIntent().getExtras();
        account = (Account) b.getSerializable("account");

        btnPlanRide = (Button) findViewById(R.id.Plan_a_Ride_btn);
        btnPlanRide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(HubActivity.this, PlanRideActivity.class);
                startActivity(i);
                //finish();
            }

        });

        btnFindRide = (Button) findViewById(R.id.Find_a_Ride_btn);
        btnFindRide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(HubActivity.this, FindRideActivity.class);
                startActivity(i);
                //finish();
            }

        });

        sendMessageTEST = (Button) findViewById(R.id.send_test_message_btn);
        sendMessageTEST.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(HubActivity.this, ChatActivity.class);
                i.putExtra("recipient_email","test2@email2.com");//TODO replace these stubs with actual values rather than stubs
                i.putExtra("recipient_id","6600222");
                startActivity(i);
                //finish();
            }

        });

        btnMyRidesTEST = (Button) findViewById(R.id.test_rides_activity);
        btnMyRidesTEST.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(HubActivity.this, RidesListActivity.class);
                i.putExtra("recipient_email","recipient@email.com");//TODO replace these stubs with actual values rather than stubs
                i.putExtra("recipient_id","8675309");
                startActivity(i);
                //finish();
            }

        });

        btnConvos = (Button) findViewById(R.id.user_conversations);
        btnConvos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(HubActivity.this, UserConversationsActivity.class);
                i.putExtra("recipient_email","sender@email.com");//TODO replace these stubs with actual values rather than stubs
                i.putExtra("recipient_id","8675309");
                startActivity(i);
                //finish();
            }

        });

        btnProfile = (Button) findViewById(R.id.profile);
        btnProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(HubActivity.this, ProfileActivity.class);
                i.putExtra("recipient_email","recipient@email.com");//TODO replace these stubs with actual values rather than stubs
                i.putExtra("recipient_id","8675309");
                i.putExtras(b);

                startActivity(i);
                //finish();
            }

        });
    }

}
