package com.example.jffalat.poolparty;

import android.app.Service;
import android.content.*;
import android.os.*;
import android.util.*;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * a background activity that extend the Service class, it periodically sends requests to the server
 * with when the device last received a message to check for and save new messages
 */
public class MessageListener extends Service {


    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;
    protected String url ="http://proj-309-gp-b-3.cs.iastate.edu/messaging/get_messages.php";
    private RequestQueue request_queue;
    protected int most_resent_message;
    protected SharedPreferences.Editor preference_editor;
    protected SharedPreferences.Editor id_list_editor;
    private AccountData user_data;
    private ArrayList<AccountData> user_ids_list;

    /**
     * an empty constructor
     */
    public MessageListener() {

    }

    /**
     * removes functionality of the onBind() method
     * @param intent
     * @return null
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * this generates and prepares the background event
     */
    @Override
    public void onCreate()
    {
        preference_editor = getSharedPreferences("LAST_MESSAGE_time", Context.MODE_PRIVATE).edit();
        id_list_editor = getSharedPreferences("USER_CONVOS_LIST", Context.MODE_PRIVATE).edit();
        SharedPreferences user_pref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        SharedPreferences message_list = getSharedPreferences("USER_CONVOS_LIST", Context.MODE_PRIVATE);
        String saved_email = user_pref.getString("EMAIL", "");
        String saved_id = user_pref.getString("USER_ID", "");
        Log.d("id ",saved_id);
        Log.d("email ",saved_email);
        user_ids_list = new ArrayList<>();
        try {
            JSONArray user_ids_JSON = new JSONArray(message_list.getString("user_ids", "[]"));
            for(int count = 0; count < user_ids_JSON.length(); count++)
            {
                user_ids_list.add(new AccountData((user_ids_JSON.getJSONObject(count))));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        Log.d("saved email",saved_email);
        Log.d("saved id",saved_id);

        user_data = new AccountData(saved_id,saved_email);
        //**/preference_editor.putInt("LAST_MESSAGE_time", 0);
        //**/preference_editor.apply();
        try
        {
            SharedPreferences lm = getSharedPreferences("LAST_MESSAGE_time", Context.MODE_PRIVATE);

            most_resent_message = lm.getInt("LAST_MESSAGE_time", 0);
            Log.d("mostresentmessage",""+most_resent_message);
            if (most_resent_message <0)
            {
                most_resent_message = 0;
                Log.d("mostresentmessagenew",""+most_resent_message);
            }

        }
        catch(NullPointerException e){
            most_resent_message = 0;
            e.printStackTrace();
            Log.d("most recent message","Null pointer");
        }

        Log.d("mostresent",""+most_resent_message);

        request_queue = Volley.newRequestQueue(this);
        StringRequest post_request; //= (StringRequest) check_request();//makes a new request to add to the request_queue

        Log.d("MessageListener", "Background service running");
        handler = new Handler();
        runnable = new Runnable(){
            @Override
            public void run() {
                handler.postDelayed(this, 10000);
                StringRequest post_request = (StringRequest) check_request();//makes a new request to add to the request_queue
                request_queue.add(post_request);
                Log.d("request url",post_request.getUrl());
            }
        };
        handler.postDelayed(runnable, 10000);
    }

    /**
     * stops the service when the application is destroyed
     */
    @Override
    public void onDestroy() {
        /**/handler.removeCallbacks(runnable);//add backslash before both *s to have the message listener terminate with the app
        Log.d("MessageListener","listener destroyed");
        /**/stopSelf();
    }


    /**
     * a method from the Service class, overridden to make a Toast to notify that the service has started
     * @param intent
     * @param startid
     */
    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
    }


    /**
     * Creates a post request that sends the user's id and email and the devices most resent messages timestamp
     * if no such value exists it is defaulted as 1. (the reason it uses the devices most recent message is so that
     * the user can access their messages from a different device (if they are logged in))
     * @return the newly created GET String request
     */
    private Request check_request()
    {
        StringRequest post_request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    public void onResponse(String response)
                    {
                        Log.d("the response: ",response);
                        if(response.startsWith("_no new messages_"))
                        {
                            Log.d("message status","no new messages");
                            //Log.d("actual response",response);
                        }
                        else if (response.startsWith("_Session Ended_"))
                        {
                            //Toast.makeText(context, "Oops! Your connection with the server has ended!", Toast.LENGTH_LONG).show();
                            //**/stopSelf();

                        }
                        else if (!response.isEmpty()||(!response.equals("[\n\n]")))
                        {
                            Log.d("message status","new messages");
                            Log.d("actual response",response);
                            //Toast.makeText(context, "New Message", Toast.LENGTH_LONG).show();
                            logMessage(response);
                        }
                        else
                        {
                            Log.d("message status","looks like something went wrong..");
                            Log.d("actual response",response);

                        }
                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("MessageLister","ResponseError");
                        error.printStackTrace();
                        stopSelf();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("sender_email", user_data.get_email());
                params.put("sender_id", user_data.get_id());
                params.put("timestamp", ""+most_resent_message);

                //params.put("sender_email", "sender@email.com");
                //params.put("sender_id", "8675309");
                //params.put("timestamp", "2405759");

                return params;
            }
        };

        return post_request;
    }

    /**
     * This method takes a JSONArray (as a String) and iterates through the the JSONArray and writes each message into a file (determined by
     * the other user's user_ID)
     * @param resp a String in JSON format that represents a JSONArray of Message objects
     */
    private void logMessage(String resp)//takes the data sent back from the server
    {
        //Log.d("log message param", resp);
        JSONArray message_JSONArray = null;
        FileOutputStream outputStream;
        StringBuilder build_string = new StringBuilder();
        try {
            Log.d("response",resp);
            message_JSONArray = new JSONArray(resp);


            for(int count = 0; count < message_JSONArray.length(); count++)
            {
                build_string.append(message_JSONArray.getJSONObject(count).toString()+"\n");
                build_string.append("\n");
                most_resent_message = message_JSONArray.getJSONObject(count).getInt("timestamp");
                Log.d("mostresentmessage",""+most_resent_message);
                String user_id;
                if(!message_JSONArray.getJSONObject(count).getString("from").equals("sender@email.com"))
                {
                    user_id = "8675309";//user_id = message_JSONArray.getJSONObject(count).getString("from");

                }
                else
                {
                    user_id = message_JSONArray.getJSONObject(count).getString("id");
                    Log.d("user_id", user_id);
                }
                if(!user_ids_list.contains(user_id))
                {
                    user_ids_list.add(new AccountData(user_id, "chat"));
                    JSONArray ids_json = new JSONArray(user_ids_list);
                    id_list_editor.putString("USER_CONVOS_LIST", ids_json.toString());
                    id_list_editor.apply();
                }
                outputStream = openFileOutput("message_user_"+user_id+".txt",  getApplicationContext().MODE_APPEND);
                outputStream.write(build_string.toString().getBytes());
                outputStream.close();


            }

            if (most_resent_message >= 0) {
                //Log.d("prefeditor",preference_editor.toString());
                preference_editor.putInt("LAST_MESSAGE_time", most_resent_message);
                preference_editor.apply();
            }

        } catch (JSONException e) {
            Log.d("messagelistener", "JSONException");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            Log.d("messagelistener", "FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("messagelistener", "IOException");
            e.printStackTrace();
        }

        //Log.d("log message", message_JSONArray.toString());

    }




}
