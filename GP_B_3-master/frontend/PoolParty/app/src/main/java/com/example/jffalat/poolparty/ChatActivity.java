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
public class ChatActivity extends ListActivity {
    protected String saved_email, saved_id;
    private Button send_message_btn;
    private EditText message_text_box;
    private TextView response_text;
    private String[] other_user = {"",""};
    private ArrayList<directmessage> message_array = new ArrayList<>();
    private AccountData other_account;
    private AccountData user_account;
    public Context context = this;
    /**
     * Called to create the Activity
     * @param savedInstanceState the instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //the following code is used to retrieve data from getExtras to identify the other user the client wishes to message
        if(bundle != null){
            other_account = new AccountData(bundle.getString("recipient_id"),bundle.getString("recipient_email"));
            other_user[0] = bundle.getString("recipient_email");
            other_user[1] = bundle.getString("recipient_id");
        }
        else //sets sets client to other user otherwise (send messages to themselves (for testing purposes))
        {
            try
            {
                SharedPreferences pref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
                other_account = new AccountData(pref.getString("USER_ID", ""),pref.getString("EMAIL", ""));
                other_user[0] = pref.getString("EMAIL", "");
                other_user[1] = pref.getString("USER_ID", "");
            }
            catch(NullPointerException e){
                e.printStackTrace();
            }

        }
        //the following code is used to retrieve user data needed to properly run
        try
        {
            SharedPreferences user_email = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
            saved_email = user_email.getString("EMAIL", "");
            saved_id = user_email.getString("USER_ID", "");
            user_account = new AccountData(saved_id,saved_email);
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }

        //the code below sends is used to actually get input and call send_message if the message is valad

        message_text_box = (EditText) findViewById(R.id.message);

        send_message_btn = (Button) findViewById(R.id.send_button);
        send_message_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            String message_text =  message_text_box.getText().toString();
            if(!message_text.isEmpty())
            {
                Log.d("message",message_text);
                //send_message(message_text);
                get_message(false);
                message_text_box.setText("");
            }

            }

        });
        get_message(false);
    }

    /**
     * This method uses Volley to send a String POST request to the server, it will Log if the message was successfully sent to the server
     * @param m the outgoing message's text
     */
    private void send_message(String m)//m is message
    {
        String send_url ="http://proj-309-gp-b-3.cs.iastate.edu/messaging/send_messages.php";
        RequestQueue request_queue = Volley.newRequestQueue(this);
        final String message = m;

        //temp. change to post
        StringRequest post_request = new StringRequest(Request.Method.POST, send_url,
                new Response.Listener<String>() {
                    /**
                     * this method logs whether the message sent successfully or not
                     * @param response the response from the server
                     */
                    @Override
                    public void onResponse(String response) {

                        Log.d("responsetag",response);//for testing purposes
                        if(response.equals("Message Sent"))
                        {
                            Log.d("ChatActivity","message sent");
                            response_text.setText(response);
                        }

                    }
                },
                new Response.ErrorListener() {
                    /**
                     * prints the error's Stack Trace as well as logs that there was a volley error
                     * @param error the error at hand
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("ChatActivity","VolleyError");///for testing
                    }
                }) {
            /**
             * creates the map to be sent by Volley with the needed data
             * i.e. sender email, sender id, recipient email, recipient id, and the message text
             *
             * @return a Map with the Parameters needed
             */
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                    if(user_account.get_email().equals("")) {
                        params.put("sender_email", "sender@email.com");
                    }
                    else {
                        Log.d("email is: ",saved_email);
                        params.put("sender_email", user_account.get_email());
                    }
                    if(saved_id.equals("")) {
                         params.put("sender_id", "8675309");
                    }
                    else {
                        Log.d("userID is: ",user_account.get_id());
                        params.put("sender_id", user_account.get_id());

                    }
                    params.put("recipient_email", other_account.get_email());
                    params.put("recipient_id", other_account.get_id());
                    params.put("message", message);

                    Log.d("messagerequestparam",params.toString());
                    return params;
            }

        };

        request_queue.add(post_request);

    }

    /**
     *This method reads all the messages from an internally stored file specified with the chat partner and displays them
     */
    private void get_message()
    {
        FileInputStream file_in;
        message_array.clear();
        File messagelog;

        try {
            messagelog =new File(context.getFilesDir(), "message_user_"+ other_account.get_id() +".txt"); // if file already exists will do nothing
            messagelog.createNewFile();//this SHOULD do nothing if the file exists
            file_in = openFileInput("message_user_"+ other_account.get_id() +".txt");//for testinguse 8675309 in place of the id
            InputStreamReader in_stream_read = new InputStreamReader(file_in);
            BufferedReader b_reader = new BufferedReader(in_stream_read);

            //StringBuilder build_string = new StringBuilder();
            String line;
            while ((line = b_reader.readLine()) != null) {

                try
                {
                    if(!line.isEmpty())
                    {
                        //Log.d("lineread",line);
                        message_array.add(new directmessage(line));
                    }
                }
                catch(JSONException e)
                {
                    Log.d("creatingmessage","JSONException");
                    e.printStackTrace();

                }
                //if(message_array.size() >= 5) message_array.remove(0);//for testing



            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            try {
                (new File(context.getFilesDir(), "message_user_" + other_account.get_id() + ".txt")).createNewFile();
            }catch(IOException e1) {
                e1.printStackTrace();
                Log.d("retrieving messages", "IOException");
            }
            Log.d("retrieving messages","filenotfound");

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("retrieving messages", "IOException");
        }
        ListMessageAdapter adapter = new ListMessageAdapter(ChatActivity.this, R.layout.messagelayout, message_array);
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

        message_array.clear();
        message_array.add(new directmessage("12345", "other@guy.test", "8675309", "you@user.test", "hey how ya doing","1"));
        message_array.add(new directmessage("8675309", "you@user.test","12345", "other@guy.test", "oh not much","2"));
        message_array.add(new directmessage("12345", "other@guy.test", "8675309", "you@user.test", "this might break everything",null));
        ListMessageAdapter adapter = new ListMessageAdapter(ChatActivity.this, R.layout.messagelayout, message_array);
        setListAdapter(adapter);
    }
}



