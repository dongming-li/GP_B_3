package com.example.jffalat.poolparty.Admin;

/**
 * a class that populates a LinearLayout that contains editTexts to input account info such as Name, Email and Password. Also contains switches to select
 * rider and driver status (this affects what the user can view and do) as well as a button to add the account
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jffalat.poolparty.LoginActivity;
import com.example.jffalat.poolparty.R;
import com.example.jffalat.poolparty.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddAccountActivity extends Activity {

    private Button btnAddAccount;
    private EditText fName;
    private EditText lName;
    private Switch driver;
    private Switch rider;
    private EditText accountEmail;
    private EditText accountPassword;
    private JSONObject newAccount = new JSONObject();
    private String firstName, lastName, email, password;
    private boolean is_driver, is_rider;
    //public static final String Email_storage="EmailStorage";

    /**
     * OnCreate Method for the AddAccountActivity
     * Registers EditTexts, Switches and Buttons as well as their onClick() listeners
     * @param savedInstanceState provides Bundle of the instance state when page is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaccount);

        fName = (EditText) findViewById(R.id.fName_txt);
        firstName = fName.getText().toString();

        lName = (EditText) findViewById(R.id.lName_txt);
        lastName = lName.getText().toString();

        accountEmail = (EditText) findViewById(R.id.account_email_txt);
        email = accountEmail.getText().toString();

        accountPassword = (EditText) findViewById(R.id.account_password_txt);
        password = accountPassword.getText().toString();

        driver = (Switch) findViewById(R.id.Driver_btn);
        is_driver = driver.isChecked();

        rider = (Switch) findViewById(R.id.Rider_btn);
        is_rider = rider.isChecked();


        btnAddAccount = (Button) findViewById(R.id.AddAccount_btn);
        btnAddAccount.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                firstName = fName.getText().toString();
                lastName = lName.getText().toString();
                email = accountEmail.getText().toString();
                password = accountPassword.getText().toString();
                is_driver = driver.isChecked();
                is_rider = rider.isChecked();


                if (acceptable_field(firstName, fName) && acceptable_field(lastName, lName) && acceptable_field(email, accountEmail) && acceptable_field(password, accountPassword)) {

                    //fills the "newAccount" JSON object before sending it
                    try {
                        newAccount.put("firstname", firstName);
                        newAccount.put("lastname", lastName);
                        newAccount.put("email", email);
                        newAccount.put("password", password);
                        newAccount.put("is_rider", is_rider);
                        newAccount.put("is_driver", is_driver);
                        if(send_data(newAccount))
                        {
                            Log.d("myTag", "success");
                            //Toast.makeText(getApplicationContext(), "Successfully added account", Toast.LENGTH_LONG).show();
                            //finish();
                            //figure out how to determine if the data was successfully added to the server
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
            if (str.length() < 30) {
                return true;
            }
            else {
                field.setText("");
                field.setHint("too long!");
            }
        } else
        {
            field.setText("");
            field.setHint("Please Enter field");
        }
        return false;
    }

    /**
     * Method to make POST request to the server. Sends account info from user input as a dictionary.
     * @param json_object JSONObject that stores user input to pass as a single parameter
     * @return
     */
    private boolean send_data(JSONObject json_object)
    {
        String url ="http://proj-309-gp-b-3.cs.iastate.edu/cgi-bin/add_user.cgi"; //TODO UPDATE URL
        RequestQueue request_queue = Volley.newRequestQueue(this);

        StringRequest post_request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("myTag",response);

                        if(!response.equals("Failure"))
                        {
                            /*try {
                                SharedPreferences.Editor preference_editor = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE).edit();
                                preference_editor.putString("EMAIL", register.getString("email"));
                                preference_editor.apply();

                                SharedPreferences test_pref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
                                String saved_pref = test_pref.getString("EMAIL", "");

                                Log.d("myTag", saved_pref);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("myTag", "error saving email");
                            }*/
                            Toast.makeText(getApplicationContext(), "Successfully added account", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(AddAccountActivity.this, AdminHubActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else
                        {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("myTag","error");
                    }
                }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                try {
                    params.put("firstname", newAccount.getString("firstname"));
                    params.put("lastname", newAccount.getString("lastname"));
                    params.put("email", newAccount.getString("email"));
                    params.put("password", newAccount.getString("password"));
                    params.put("is_rider", newAccount.getString("is_rider"));
                    params.put("is_driver", newAccount.getString("is_driver"));
                    return params;
                }catch(JSONException e){
                    e.printStackTrace();
                }
                return null;
            }
        };
        Log.d("POST URL",post_request.toString());
        request_queue.add(post_request);
        return false;
    }



}
