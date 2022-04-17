package com.example.jffalat.poolparty.Admin;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jffalat.poolparty.Account;
import com.example.jffalat.poolparty.R;

import java.util.List;

/**
 * a class that creates a custom adapter to inflate and populate each row of the ListView in ListAccounts
 */

public class ListAccountAdapter extends ArrayAdapter<Account> {

    private Context context;
    private int layoutResourceId;
    private List<Account> Accounts = null;

    /**
     * Constructor for a new ListAccountAdapter
     * @param context the current context
     * @param layoutResourceId the id to represent the layout
     * @param data a list of information for each row in the list view
     */
    public ListAccountAdapter(Context context, int layoutResourceId, List<Account> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.Accounts = data;
    }

    /**
     * This method inflates the custom row layout and populates the field with a holder object
     * @param position integer to identify the row in the ListView
     * @param convertView View being populated
     * @param parent
     * @return
     */
    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AccountHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            //create a new ItunesHolder and set it to the fields the row in the list view
            holder = new AccountHolder();

            //set the holder view id's
            holder.email = (TextView) row.findViewById(R.id.textView1);

            row.setTag(holder);
        } else {
            holder = (AccountHolder) row.getTag();
        }

        //get the current position from the list
        Account account = Accounts.get(position);

        //set the text for the row
        holder.email.setText(account.getEmail());
        return row;
    }

    /**
     * A class to represent the fields in the row layout
     */
    static class AccountHolder
    {
        TextView email;
    }
}