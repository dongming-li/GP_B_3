package com.example.jffalat.poolparty.Admin;

/**
 * a class that populates a LinearLayout that verifies with the Admin that they want to delete the current account.
 * Contains Buttons to confirm the account deletion or cancel
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jffalat.poolparty.Account;
import com.example.jffalat.poolparty.HubActivity;
import com.example.jffalat.poolparty.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeleteAccountActivity extends Activity {

    private Button btnYesDelete, btnNoDelete;
    private Account acc;
    private JSONObject obj = new JSONObject();
    private JSONObject account = new JSONObject();
    private String firstName, lastName, email, password;
    private boolean is_driver, is_rider;

    /**
     * OnCreate Method for the DeleteAccountActivity
     * Registers Buttons as well as their onClick() listeners
     * @param savedInstanceState provides Bundle of the instance state when page is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteaccount);

        Bundle b = getIntent().getExtras();
        Log.d("tag","check if bundle is null");
        if(b != null){
            Log.d("tag","bundle not null");
            acc = (Account) b.getSerializable("account");
            try {
                obj = new JSONObject(b.getString("json"));
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }



        btnYesDelete = (Button) findViewById(R.id.yes_delete_btn);
        btnYesDelete.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                try {
                    account.put("email", acc.getEmail());
                    account.put("new_password", acc.getPassword());
                    account.put("new_firstname", acc.getFirstname());
                    account.put("new_lastname", acc.getLastname());
                    account.put("new_email", acc.getEmail());
                    if (acc.getDriver()){
                        account.put("new_driver_status", "1");
                    }
                    else if (!acc.getDriver()){
                        account.put("new_driver_status", "0");
                    }
                    if (acc.getRider()){
                        account.put("new_rider_status", "1");
                    }
                    else if (!acc.getRider()){
                        account.put("new_rider_status", "0");
                    }
                    account.put("delete_account", "1");
                    account.put("new_ban_status", "0");
                    account.put("new_admin_status", "0");
                }
                catch (JSONException e){

                }
                if (send_data(account)) {
                    Log.d("myTag","success");

                }

            }

        });

        btnNoDelete = (Button) findViewById(R.id.no_delete_btn);
        btnNoDelete.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeleteAccountActivity.this, AdminHubActivity.class);
                startActivity(i);
                finish();
            }

        });
    }

//    private boolean acceptable_field(String str, EditText field)
//    {
//        if (!str.equals("")) {
//            if (str.length() < 30) {
//                return true;
//            }
//            else {
//                field.setText("");
//                field.setHint("too long!");
//            }
//        } else
//        {
//            field.setText("");
//            field.setHint("Please Enter field");
//        }
//        return false;
//    }

    /**
     * Method to make POST request to the server. Sends account info to be deleted as a dictionary.
     * @param json_object JSONObject that stores user input to pass as a single parameter
     * @return
     */
    private boolean send_data(final JSONObject json_object)
    {
        String url ="http://proj-309-gp-b-3.cs.iastate.edu/change_user.php"; //TODO UPDATE URL
        RequestQueue request_queue = Volley.newRequestQueue(this);
        final JSONObject json = json_object;

        StringRequest post_request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("myTag",response);
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Successfully deleted account", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(DeleteAccountActivity.this, AdminHubActivity.class);
                        i.putExtra("delete",acc.getEmail());
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
                    params.put("email", account.getString("email"));
                    params.put("new_password", account.getString("new_password"));
                    params.put("new_firstname", account.getString("new_firstname"));
                    params.put("new_lastname", account.getString("new_lastname"));
                    params.put("new_email", account.getString("email"));
                    params.put("new_driver_status", account.getString("new_driver_status"));
                    params.put("new_rider_status", account.getString("new_rider_status"));
                    params.put("new_admin_status", "0");
                    params.put("delete_account", "1");
                    params.put("new_ban_status", "0");

                    //Toast.makeText(getApplicationContext(), params.toString(), Toast.LENGTH_LONG).show();

                    return params;
                }catch(JSONException e){
                    e.printStackTrace();
                }
                return null;

               /* params.put("email", "demo");
                params.put("new_password", "demo");
                params.put("new_firstname", "demo");
                params.put("new_lastname", "demo");
                params.put("new_email", "demo");
                params.put("new_rider_status", "1");
                params.put("new_driver_status", "1");
                params.put("new_admin_status", "0");
                params.put("new_ban_status", "0");
                params.put("delete_account", "1"); */


            }
        };
        Log.d("POST URL",post_request.toString());
        request_queue.add(post_request);
        return false;
    }



}
