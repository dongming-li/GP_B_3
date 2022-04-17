package com.example.jffalat.poolparty.Admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jffalat.poolparty.Account;
import com.example.jffalat.poolparty.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * a class that populates a ListView to display all created accounts to an Admin. Utilizes onListItemClick() to launch new
 * intent and display account info for the selected account
 */

public class ListAccounts extends ListActivity {

    private JSONObject user1 = new JSONObject();
    private JSONObject user2 = new JSONObject();
    private JSONArray jsonArray = new JSONArray();
    private boolean rider = true;
    private boolean driver = true;
    private Account account;
    private String string;
    private String[] emails;
    private String stringTest = "{\"rating\":5,\"premium\':0,\"firstname\":\"addAccount\",\"lastname\":\"AdminTest\",\"number_of_ratings\":1,\"driver\":1,\"password\":\"password\",\"number_of_trips\":0,\"UID\":1988,\"admin\":0,\"rider\":1,\"ban\":0,\"email\":\"testAdmin@gmail.com\"}";
    private JSONObject userAccount = new JSONObject();



    /**
     * OnCreate Method for the ListAccounts
     * Creates and sets new custom ListAccountAdapter to populate the rows of the ListView
     * @param savedInstanceState provides Bundle of the instance state when page is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_list_accounts);

    //TODO: receive StringArray of accounts from database
           emails = getIntent().getStringArrayExtra("email");
        try {
            userAccount = new JSONObject(stringTest);
        }
        catch (JSONException e){

        }
        //Toast.makeText(getApplicationContext(), emails[2], Toast.LENGTH_LONG).show();

    //NOTE: building array for testing
    /*try {
        user1.put("firstname", "John");
        user1.put("lastname", "Smith");
        user1.put("email", "JohnSmith@gmail.com");
        user1.put("password", "password");
        user1.put("is_driver", "true");
        user1.put("is_rider", "false");

        user2.put("firstname", "Jane");
        user2.put("lastname", "Doe");
        user2.put("email", "JaneDoe@gmail.com");
        user2.put("password", "pass");
        user2.put("is_driver", "false");
        user2.put("is_rider", "true");

        jsonArray.put(user1);
        jsonArray.put(user2);
    }
    catch (JSONException e){

    }*/

    //Test Code ends, Code starts
        String email = "";
        String accountType = "";
        boolean is_driver;
        boolean is_rider;
        //Log.d("Result", result);

        ArrayList<Account> data = new ArrayList<Account>();

        for (int i = 0; i < emails.length; i++){
                account = new Account(null, null, null, null, false, false, false);
                account.setEmail(emails[i]);
                data.add(account);
        }

        ListAccountAdapter adapter = new ListAccountAdapter(ListAccounts.this, R.layout.admin_rowlayout, data);
        setListAdapter(adapter);
    }

    /**
     * This method sends a POST request to the server to get the account info of the selected account,
     * if successful, a new intent displaying this account info will launch
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        if (send_data(emails[position])) {
        }
        else {
        //    Toast.makeText(getApplicationContext(), "Error has occurred during POST Request", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * onResume method that calls super.onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * onPause method that calls super.onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
    }


    /**
     * Method to make POST request to the server. Sends selected email as a dictionary.
     * @param mail String that contains the email of the selected account to send to the server as an identifier
     * @return
     */
    private boolean send_data(String mail)
    {
        String url ="http://proj-309-gp-b-3.cs.iastate.edu/get_user_info.php"; //TODO UPDATE URL
        RequestQueue request_queue = Volley.newRequestQueue(this);
        final String mails = mail;

        StringRequest post_request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("myTag",response);
                        string = response;
                        //Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
                        JSONObject json = new JSONObject();
                        Account acc = new Account(null, null, null, null, false, false, false);

                        try {
                            //JSONObject json = new JSONObject("{\"firstname\":\"addAccount\",\"lastname\":\"AdminTest\",\"driver\":1,\"password\":\"password\",\"rider\":1,\"email\":\"testAdmin@gmail.com\"}");
                            json = new JSONObject(string);
                            //acc = new Account(json.getString("firstname"), json.getString("lastname"), json.getString("email"), json.getString("password"), json.getBoolean("rider"), json.getBoolean("driver"));
                            acc.setFirstname(json.getString("firstname"));
                            acc.setLastname(json.getString("lastname"));
                            acc.setEmail(json.getString("email"));
                            acc.setPassword(json.getString("password"));
                            if (json.getString("driver") == "1")
                            {
                                acc.setDriver(true);
                            }
                            else if (json.getString("driver") == "0")
                            {
                                acc.setDriver(false);
                            }
                            if (json.getString("rider") == "1")
                            {
                                acc.setRider(true);
                            }
                            else if (json.getString("rider") == "0")
                            {
                                acc.setRider(false);
                            }
                            //acc.setDriver(json.getBoolean("driver"));
                            //acc.setRider(json.getBoolean("rider"));

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "json error", Toast.LENGTH_LONG).show();
                        }

                        Intent i = new Intent(ListAccounts.this, AccountDetailsActivity.class);
                        Bundle b = new Bundle();
                        b.putSerializable("account", acc);
                        //Toast.makeText(getApplicationContext(), acc.getEmail(), Toast.LENGTH_LONG).show();
                        b.putString("json", json.toString());
                        i.putExtras(b);
                        startActivity(i);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("myTag","error");
                        Toast.makeText(getApplicationContext(), "Error with POST", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("email", mails);
                return params;
            }
        };
        Log.d("POST URL",post_request.toString());
        request_queue.add(post_request);
        return false;
    }
}
