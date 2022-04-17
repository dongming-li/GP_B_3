package com.example.jffalat.poolparty.Admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jffalat.poolparty.Account;
import com.example.jffalat.poolparty.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * a class that populates a LinearLayout that contains profile information (name, email, password, rider and driver status)
 * as well as buttons to update and delete an account as an Admin user
 */

public class AccountDetailsActivity extends Activity {

    Button btnUpdateAccounts;
    Button btnDeleteAccount;
    JSONObject obj = new JSONObject();
    private Account account;


    /**
     * OnCreate Method for the Account Details activity in Admin user-case
     * Registers Buttons as well as their onClick() listeners
     * @param savedInstanceState provides Bundle of the instance state when page is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_account_details);

        // Determine which event was pressed
        Bundle b = getIntent().getExtras();
        account = (Account) b.getSerializable("account");
        try {
            obj = new JSONObject(b.getString("json"));
            //Toast.makeText(getApplicationContext(), obj.toString(), Toast.LENGTH_LONG).show();
        }
        catch (JSONException e){
            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

            TextView temp = (TextView) findViewById(R.id.firstname);
            temp.setText("First Name: " + account.getFirstname());

            temp = (TextView) findViewById(R.id.lastname);
            temp.setText("Last Name: " + account.getLastname());

            temp = (TextView) findViewById(R.id.email);
            temp.setText("Email: " + account.getEmail());

            temp = (TextView) findViewById(R.id.password);
            temp.setText("Password: " + account.getPassword());

            temp = (TextView) findViewById(R.id.driver);
            temp.setText("Driver: " + account.getDriver());

            temp = (TextView) findViewById(R.id.rider);
            temp.setText("Rider: " + account.getRider());


        btnUpdateAccounts = (Button) findViewById(R.id.update_account_btn);
        btnUpdateAccounts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    Intent updateIntent = new Intent(AccountDetailsActivity.this, UpdateAccountActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("account", account);
                    bundle.putString("json", obj.toString());
                    updateIntent.putExtras(bundle);
                    startActivity(updateIntent);
            }

        });

        btnDeleteAccount = (Button) findViewById(R.id.delete_account_btn);
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent deleteIntent = new Intent(AccountDetailsActivity.this, DeleteAccountActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("account", account);
                bundle.putString("json", obj.toString());
                deleteIntent.putExtras(bundle);
                startActivity(deleteIntent);
            }

        });

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



}
