package com.example.jffalat.poolparty.Admin;
/**
 * a class that populates a LinearLayout for the AdminHub. Contains two buttons for adding an account and viewing all created accounts
 */

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.content.Intent;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonArrayRequest;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.example.jffalat.poolparty.R;
        import com.example.jffalat.poolparty.Rider.DisplayRidesActivity;
        import com.example.jffalat.poolparty.Rider.FindRideActivity;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;

        import static android.R.id.list;


public class AdminHubActivity extends AppCompatActivity {

    private Button btnAddAccount;
    private Button btnListAccounts;
    //private JSONObject emails = new JSONObject();
    private String string;
    private String[] fixedEmails;

    /**
     * OnCreate Method for the AdminHubActivity in Admin user-case.
     * Registers Buttons as well as their onClick() listeners,
     * onClick() listener for "List Accounts" makes a GET request and receives string of emails of created accounts
     * @param savedInstanceState provides Bundle of the instance state when page is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhub);

        final RequestQueue request_queue = Volley.newRequestQueue(this);
        final String url ="http://proj-309-gp-b-3.cs.iastate.edu/get_all_emails.php";

        btnAddAccount = (Button) findViewById(R.id.Add_Account_btn);
        btnAddAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminHubActivity.this, AddAccountActivity.class);
                startActivity(i);
                //finish();
            }

        });

        btnListAccounts = (Button) findViewById(R.id.List_Accounts_btn);
        btnListAccounts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                string = response;
                                //Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject json = new JSONObject(string);
                                    String[] emails = string.split(":|,");
                                    String[] temp = new String[emails.length];
                                    String[] temp2 = new String[emails.length];
                                    String[] temp3 = new String[emails.length];
                                    for (int i = 0; i < emails.length; i++) {
                                        temp[i] = (emails[i].replaceAll("\\[", ""));
                                        temp2[i] = (emails[i].replaceAll("]", ""));
                                        temp3[i] = (emails[i].replaceAll("\"", ""));
                                        emails[i] = (temp3[i]);
                                    }
                                    fixedEmails = new String[emails.length - 1];
                                        for (int j = 0; j < emails.length - 1; j++) {

                                            if (j == 0) {
                                                if (fixedEmails.length != 0) {
                                                    if (fixedEmails.length == 1) {
                                                        fixedEmails[j] = temp3[j + 1].substring(3, temp3[1].length() - 3);
                                                    } else {
                                                        fixedEmails[j] = temp3[j + 1].substring(3, temp3[j + 1].length() - 1);
                                                    }
                                                }
                                            } else if (j == emails.length - 2) {
                                                fixedEmails[j] = temp3[j + 1].substring(2, temp3[j + 1].length() - 3);
                                            } else
                                                fixedEmails[j] = temp3[j + 1].substring(2, temp3[j + 1].length() - 1);
                                        }

                                        Intent i = new Intent(AdminHubActivity.this, ListAccounts.class);
                                        i.putExtra("email", fixedEmails);
                                        startActivity(i);



                                }
                                catch (JSONException e) {

                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Could not retrieve emails", Toast.LENGTH_LONG).show();
                    }
                });

                request_queue.add(stringRequest);

            }

        });
    }

}
