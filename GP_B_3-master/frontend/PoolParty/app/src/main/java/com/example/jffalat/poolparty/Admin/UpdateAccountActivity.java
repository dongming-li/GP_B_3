package com.example.jffalat.poolparty.Admin;

/**
 * a class that populates a LinearLayout that contains editTexts to edit account info such as Name, Email and Password. Also contains switches to select
 * rider and driver status (this affects what the user can view and do) as well as a button to update the account
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
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.example.jffalat.poolparty.Account;
        import com.example.jffalat.poolparty.R;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.Map;

public class UpdateAccountActivity extends Activity {

    private Button btnUpdateAccount;
    private EditText fName;
    private EditText lName;
    private Switch driver;
    private Switch rider;
    private EditText accountEmail;
    private EditText accountPassword;
    private JSONObject account = new JSONObject();
    private JSONObject obj = new JSONObject();
    private String firstName, lastName, email, password;
    private boolean is_driver, is_rider;
    private Account acc;
    //public static final String Email_storage="EmailStorage";

    /**
     * OnCreate Method for the UpdateAccountActivity
     * Registers EditTexts, Switches and Buttons as well as their onClick() listeners
     * @param savedInstanceState provides Bundle of the instance state when page is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateaccount);

        //Get Account info from previous activity
        Bundle b = getIntent().getExtras();
        try {
            obj = new JSONObject(b.getString("json"));
            acc = (Account) b.getSerializable("account");
        }
        catch(JSONException e){

        }


            //final Account accountInfo = new Account(obj.getString("firstname"), obj.getString("lastname"), obj.getString("email"), obj.getString("password"), obj.getBoolean("rider"), obj.getBoolean("driver"));
            fName = (EditText) findViewById(R.id.fName_txt);
            fName.setText(acc.getFirstname());

            lName = (EditText) findViewById(R.id.lName_txt);
            lName.setText(acc.getLastname());

            accountEmail = (EditText) findViewById(R.id.account_email_txt);
            accountEmail.setText(acc.getEmail());

            accountPassword = (EditText) findViewById(R.id.account_password_txt);
            accountPassword.setText(acc.getPassword());

            driver = (Switch) findViewById(R.id.Driver_btn);
            driver.setChecked(acc.getDriver());

            rider = (Switch) findViewById(R.id.Rider_btn);
            rider.setChecked(acc.getRider());






        btnUpdateAccount = (Button) findViewById(R.id.UpdateAccount_btn);
        btnUpdateAccount.setOnClickListener(new View.OnClickListener() {


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
                        account.put("firstname", firstName);
                        account.put("lastname", lastName);
                        account.put("email", email);
                        account.put("password", password);
                        if (is_driver)
                            account.put("driver", "1");
                        else
                            account.put("driver", "0");
                        if (is_rider)
                            account.put("rider", "1");
                        else
                            account.put("rider", "0");
                        account.put("admin", "0");
                        account.put("delete", "0");
                        account.put("new_ban_status", "0");

                        if(send_data(account))
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
    private boolean send_data(final JSONObject json_object)
    {
        String url ="http://proj-309-gp-b-3.cs.iastate.edu/change_user.php";
        RequestQueue request_queue = Volley.newRequestQueue(this);

        StringRequest post_request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("myTag",response);
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Successfully updated account", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(UpdateAccountActivity.this, AdminHubActivity.class);
                        startActivity(i);
                        finish();
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
                    //JSONObject json = new JSONObject(firstName);
                    params.put("email", account.getString("email"));
                    params.put("new_password", account.getString("password"));
                    params.put("new_firstname", account.getString("firstname"));
                    params.put("new_lastname", account.getString("lastname"));
                    params.put("new_email", account.getString("email"));
                    params.put("new_driver_status", account.getString("driver"));
                    params.put("new_rider_status", account.getString("rider"));
                    params.put("new_admin_status", account.getString("admin"));
                    params.put("delete_account", account.getString("delete"));
                    params.put("new_ban_status", account.getString("new_ban_status"));

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

