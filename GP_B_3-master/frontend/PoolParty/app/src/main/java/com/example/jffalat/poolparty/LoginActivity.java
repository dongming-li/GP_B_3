package com.example.jffalat.poolparty;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jffalat.poolparty.Admin.AdminHubActivity;
import com.example.jffalat.poolparty.Driver.DriverHubActivity;
import com.example.jffalat.poolparty.Rider.RiderHubActivity;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * the first activity opened on startup
 * the user must either sign up(and be sent to the signup activity),
 * or sign in with email and password (and ve sent to the main hub if valid)
 * if the user is an admin, they are sent to a different page specifically for admins instad of the mainhub
 */
public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private String email, password, string;
    private boolean login_success, login_post = true, accountType_post = false, accountType_success;
    private JSONObject login = new JSONObject();
    final JSONObject obj = new JSONObject();


    /**
     * This generates the layout of the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        inputEmail = (EditText) findViewById(R.id.Email_txt);
        email = inputEmail.getText().toString();

        inputPassword = (EditText) findViewById(R.id.Password_txt);
        password = inputPassword.getText().toString();


        btnLogin = (Button) findViewById(R.id.Login_btn);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Check that email and password are not empty and are correct

                if ((email != null && password != null) && (email != "" && password != "")) {
                    try {
                        login.put("email", email);
                        login.put("password", password);

                        send_data("http://proj-309-gp-b-3.cs.iastate.edu/login.php");
                        if (login_success) {
                            Log.d("tag", "success");
                            //Toast.makeText(getApplicationContext(), "Proceed to account check", Toast.LENGTH_LONG).show();

                            //Check the user account type and launch proper user case
                            check_account("http://proj-309-gp-b-3.cs.iastate.edu/get_user_info.php");
                            if (accountType_success) {
                                Log.d("tag", "success");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();


               if (email.equals("admin") && password.equals("adminPassword")) {
                    Intent admin = new Intent(LoginActivity.this, AdminHubActivity.class);
                    admin.putExtra("username", email);
                    startActivity(admin);
                }

            /*   else {
                   Intent i = new Intent(LoginActivity.this, HubActivity.class);
                   SharedPreferences.Editor user_pref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE).edit();
                   user_pref.putString("USER_ID", "8675309");//TODO use actual user data not stubs
                   user_pref.putString("EMAIL", "recipient@email.com");
                   user_pref.apply();
                    i.putExtra("username", email);
                    startActivity(i);
                    //finish();
                }
                */

            }

        });

        btnLinkToRegister = (Button) findViewById(R.id.Register_Link_btn);
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //logic to start HintActivity
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }

        });

    }

    /**
     * Method to make POST request to the server. Sends user input (email and password) to server for verification
     * @param url String that contains the url to POST to
     */
    private boolean send_data(String url) {
        RequestQueue request_queue = Volley.newRequestQueue(this);

        StringRequest post_request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            Log.d("tag", response);//for testing purposes
                            Log.d("tag", "success");
                            string = response;
                            //Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
                            int responseNum = Integer.parseInt(string);
                            switch (responseNum){
                                case 0:
                                    Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_LONG).show();
                                    login_success = true;
                                    check_account("http://proj-309-gp-b-3.cs.iastate.edu/get_user_info.php");
                                    break;
                                case 1:
                                    //Toast.makeText(getApplicationContext(), "Incorrect Username or Password", Toast.LENGTH_LONG).show();
                                    break;
                                case 2:
                                    //Toast.makeText(getApplicationContext(), "Blank Password or No Account", Toast.LENGTH_LONG).show();
                                    break;
                                case 3:
                                    //Toast.makeText(getApplicationContext(), "POST Error", Toast.LENGTH_LONG).show();
                                    break;
                            }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("myTag", "error");
                        Toast.makeText(getApplicationContext(), "Post Request Error", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                try {
                    params.put("email", login.getString("email"));
                    params.put("password", login.getString("password"));
                    return params;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Log.d("POST URL", post_request.toString());
        request_queue.add(post_request);
            return false;
    }

    /**
     * Method to make POST request to the server. Sends verified email as identifier to determine account type
     * @param mail String that contains the email of the successfully logged in user
     */
    private boolean check_account (String mail)
    {
        String url ="http://proj-309-gp-b-3.cs.iastate.edu/get_user_info.php";
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
                            if (json.getString("admin") == "1")
                            {
                                acc.setAdmin(true);
                            }
                            else if (json.getString("admin") == "0")
                            {
                                acc.setAdmin(false);
                            }
                            //acc.setDriver(json.getBoolean("driver"));
                            //acc.setRider(json.getBoolean("rider"));

                            //check account type and launch proper intent activity
                               /* if(json.getString("admin") == "1") {

                                }*/
                               accountType_success = true;

                            if (acc.getDriver() && acc.getRider()) {
                                Intent i = new Intent(LoginActivity.this, HubActivity.class);
                                SharedPreferences.Editor user_pref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE).edit();
                                user_pref.putString("USER_ID", "8675309");//TODO use actual user data not stubs
                                user_pref.putString("EMAIL", "recipient@email.com");
                                user_pref.apply();
                                Bundle b = new Bundle();
                                b.putSerializable("account", acc);
                                //Toast.makeText(getApplicationContext(), acc.getEmail(), Toast.LENGTH_LONG).show();
                                b.putString("json", json.toString());
                                i.putExtras(b);
                                i.putExtra("username", email);
                                startActivity(i);
                                finish();
                            }

                            if (acc.getDriver() && !acc.getRider())
                            {
                                Intent i = new Intent(LoginActivity.this, DriverHubActivity.class);
                                SharedPreferences.Editor user_pref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE).edit();
                                user_pref.putString("USER_ID", "8675309");//TODO use actual user data not stubs
                                user_pref.putString("EMAIL", "recipient@email.com");
                                user_pref.apply();
                                Bundle b = new Bundle();
                                b.putSerializable("account", acc);
                                //Toast.makeText(getApplicationContext(), acc.getEmail(), Toast.LENGTH_LONG).show();
                                b.putString("json", json.toString());
                                i.putExtras(b);
                                i.putExtra("username", email);
                                startActivity(i);
                                finish();
                            }

                            if (!acc.getDriver() && acc.getRider())
                            {
                                Intent i = new Intent(LoginActivity.this, RiderHubActivity.class);
                                SharedPreferences.Editor user_pref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE).edit();
                                user_pref.putString("USER_ID", "8675309");//TODO use actual user data not stubs
                                user_pref.putString("EMAIL", "recipient@email.com");
                                user_pref.apply();
                                Bundle b = new Bundle();
                                b.putSerializable("account", acc);
                                //Toast.makeText(getApplicationContext(), acc.getEmail(), Toast.LENGTH_LONG).show();
                                b.putString("json", json.toString());
                                i.putExtras(b);
                                i.putExtra("username", email);
                                startActivity(i);
                                finish();
                            }

                            else if (acc.getAccountType() == 0){
                                Toast.makeText(getApplicationContext(), "Check if Admin", Toast.LENGTH_LONG).show();
                                Intent admin = new Intent(LoginActivity.this, AdminHubActivity.class);
                                Bundle b = new Bundle();
                                b.putSerializable("account", acc);
                                //Toast.makeText(getApplicationContext(), acc.getEmail(), Toast.LENGTH_LONG).show();
                                b.putString("json", json.toString());
                                admin.putExtras(b);
                                admin.putExtra("username", email);
                                startActivity(admin);
                                finish();
                            }



                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Account error", Toast.LENGTH_LONG).show();
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
                params.put("email", email);
                return params;
            }
        };
        Log.d("POST URL",post_request.toString());
        request_queue.add(post_request);
        return false;
    }
}
