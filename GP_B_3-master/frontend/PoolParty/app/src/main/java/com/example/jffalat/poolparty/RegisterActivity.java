package com.example.jffalat.poolparty;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;
import org.json.JSONException;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * The activity for new users to set up a new account
 * makes a request to the URL:
 *      http://proj-309-gp-b-3.cs.iastate.edu/cgi-bin/add_user.cgi
 *      to create the account
 */
public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText fName;
    private EditText lName;
    private Switch driver;
    private Switch rider;
    private EditText registerEmail;
    private EditText registerPassword;
    private JSONObject register = new JSONObject();
    private String firstName, lastName, email, password;
    private boolean is_driver, is_rider;
    public static final String Email_storage="EmailStorage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fName = (EditText) findViewById(R.id.fName_txt);
        firstName = fName.getText().toString();

        lName = (EditText) findViewById(R.id.lName_txt);
        lastName = lName.getText().toString();

        registerEmail = (EditText) findViewById(R.id.register_email_txt);
        email = registerEmail.getText().toString();

        registerPassword = (EditText) findViewById(R.id.register_password_txt);
        password = registerPassword.getText().toString();

        driver = (Switch) findViewById(R.id.Driver_btn);
        is_driver = driver.isChecked();

        rider = (Switch) findViewById(R.id.Rider_btn);
        is_rider = rider.isChecked();


        btnRegister = (Button) findViewById(R.id.Register_btn);
        btnRegister.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                firstName = fName.getText().toString();
                lastName = lName.getText().toString();
                email = registerEmail.getText().toString();
                password = registerPassword.getText().toString();
                is_driver = driver.isChecked();
                is_rider = rider.isChecked();


                if (acceptable_field(firstName, fName) && acceptable_field(lastName, lName) && acceptable_field(email, registerEmail) && acceptable_field(password, registerPassword)) {

                    //fills the "regester" JSON object before sending it
                    try {
                        register.put("firstname", firstName);
                        register.put("lastname", lastName);
                        register.put("email", email);
                        register.put("password", password);
                        if(send_data(register))
                        {
                            Log.d("myTag", "success");
                            //figure out how to determine if the data was successfully added to the server
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }


            }

        });

        btnLinkToLogin = (Button) findViewById(R.id.Login_Link_btn);
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }

        });
    }

    /**
     * checks to make sure that the EditText inputs are valid, and will clear them and place hints saying what is wrong if not valad
     * @param str  the string in the edittext being checked
     * @param field the EditText being checked
     * @return false if the field is not valid and otherwise returns true
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
     * sends data to the server to be validated/saved, status of the response is then returned by the server
     * @param json_object the data being send to the server in JSON format
     */
    private boolean send_data(JSONObject json_object)
            {
                String url ="http://proj-309-gp-b-3.cs.iastate.edu/cgi-bin/add_user.cgi";
                RequestQueue request_queue = Volley.newRequestQueue(this);

                StringRequest post_request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("myTag",response);
                                if(!response.equals("Failure"))
                                {
                                    try {
                                        SharedPreferences.Editor preference_editor = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE).edit();
                                        preference_editor.putString("EMAIL", register.getString("email"));

                                        JSONObject jsonresp = new JSONObject(response);

                                        preference_editor.putString("USER_ID", jsonresp.getString("user_id"));
                                        preference_editor.apply();

                                        //SharedPreferences test_pref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
                                        //String saved_pref = test_pref.getString("EMAIL", "");

                                        //Log.d("myTag", saved_pref);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.d("myTag", "error saving email");
                                    }
                                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
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
                        })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<>();
                        // the POST parameters:
                        try {
                            params.put("firstname", register.getString("firstname"));
                            params.put("lastname", register.getString("lastname"));
                            params.put("email", register.getString("email"));
                            params.put("password", register.getString("password"));
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
