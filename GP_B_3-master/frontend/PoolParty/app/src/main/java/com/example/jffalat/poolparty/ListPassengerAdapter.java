package com.example.jffalat.poolparty;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.jffalat.poolparty.Ride;
import com.example.jffalat.poolparty.R;

import java.util.List;

/**
 * An Adapter used to populate a LView with a list of riders, and extends the ArrayAdapter<> class
 */
public class ListPassengerAdapter extends ArrayAdapter<AccountData> {

    private Context context;
    private int layoutResourceId;
    private List<AccountData> passengers = null;
    private AccountData accountdata;

    /**
     * the constructer of the ListPassengersAdapter
     * @param context the Activity where the new instance of ListPassengerAdapter is instantiated
     * @param layoutResourceId the id of the layout the List (data) will manifest
     * @param data the List of accounts to populate the View with
     */
    public ListPassengerAdapter(Context context, int layoutResourceId, List<AccountData> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.passengers = data;
    }


    /**
     *
     * @param position the index of the position in the List
     * @param convertView the specific element being added to the view
     * @param parent the view that the element is being added into
     * @return the newly inflated, created, and populated View to be added
     */
    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PassengerHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PassengerHolder();

            holder.email = (TextView) row.findViewById(R.id.email);
            holder.id = (TextView) row.findViewById(R.id.user_id);
            holder.rating = (TextView) row.findViewById(R.id.rating);
            holder.message_btn = (Button) row.findViewById(R.id.message_btn);

            row.setTag(holder);
        } else {
            holder = (PassengerHolder) row.getTag();
        }


        accountdata = passengers.get(position);

        Log.d("ListPass.Adapt.","starting row");
        holder.email.setText(accountdata.get_email());
        holder.id.setText(accountdata.get_id());
        holder.rating.setText(accountdata.get_rating());
        holder.message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChatActivity.class);
                i.putExtra("recipient_email",accountdata.get_email());
                i.putExtra("recipient_id",accountdata.get_id());
                context.startActivity(i);
            }
        });

        return row;
    }

    /**
     * A class to represent the fields in the row layout
     */
    static class PassengerHolder
    {
        TextView email;
        TextView id;
        TextView rating;
        Button message_btn;
    }
}