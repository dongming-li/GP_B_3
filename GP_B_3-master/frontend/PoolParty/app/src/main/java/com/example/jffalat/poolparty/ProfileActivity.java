package com.example.jffalat.poolparty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jffalat.poolparty.Admin.DeleteAccountActivity;
import com.example.jffalat.poolparty.Admin.UpdateAccountActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jffalat on 10/24/2017.
 *
 * a class that populates a LinearLayout that contains the user's account info including name, email, password
 * and rider/driver status. Contains button to update account as a valid user of that account.
 */

public class ProfileActivity extends Activity{

    Button btnUpdateAccounts;
    Button btnDeleteAccount;
    JSONObject obj = new JSONObject();
    private Account account;

    /**
     * Called to create the Activity
     * @param savedInstanceState the instance state
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

                Intent updateIntent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("account", account);
                bundle.putString("json", obj.toString());
                updateIntent.putExtras(bundle);
                startActivity(updateIntent);
                finish();
            }

        });

        btnDeleteAccount = (Button) findViewById(R.id.delete_account_btn);
        btnDeleteAccount.setVisibility(View.GONE);

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

