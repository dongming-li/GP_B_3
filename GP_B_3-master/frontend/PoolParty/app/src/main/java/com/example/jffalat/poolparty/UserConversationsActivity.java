package com.example.jffalat.poolparty;

import android.app.ListActivity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jffalat.poolparty.Driver.PlanRideActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * This Activity is used to chat with a specific user that is determined in the intents Extras.
 */
public class UserConversationsActivity extends ListActivity {

    private ArrayList<AccountData> convos_list = new ArrayList<>();
    public Context context = this;
    /**
     * Called to create the Activity
     * @param savedInstanceState the instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_conversations);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        SharedPreferences message_list = getSharedPreferences("USER_CONVOS_LIST", Context.MODE_PRIVATE);
        convos_list = new ArrayList<>();
        try {
            JSONArray user_ids_JSON = new JSONArray(message_list.getString("user_ids", "[]"));
            for(int count = 0; count < user_ids_JSON.length(); count++)
            {
                convos_list.add(new AccountData((user_ids_JSON.getJSONObject(count))));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        get_message(true);
    }
    /**
     *This method reads all the messages from an internally stored file specified with the chat partner and displays them
     */
    private void get_message()
    {


        ListPassengerAdapter adapter = new ListPassengerAdapter(UserConversationsActivity.this, R.layout.user_data_layout, convos_list);
        setListAdapter(adapter);

    }

    /**
     * an overloaded version of get_message() that includes a toggle boolean that uses preset variable stubs to test it
     * @param debug a boolean that sets the message array to preset values (for testing) if true, and if false: it calls the original
     *              get_message() method
     */
    private void get_message(boolean debug)
    {
        if(!debug)
        {
            get_message();
            return;
        }

        convos_list.clear();
        convos_list.add(new AccountData("8675309","Jenny@gmail.com"));
        convos_list.add(new AccountData("1234567","count@dracula.com"));
        ListPassengerAdapter adapter = new ListPassengerAdapter(UserConversationsActivity.this, R.layout.user_data_layout, convos_list);
        setListAdapter(adapter);
    }
}



