package com.example.jffalat.poolparty;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jffalat.poolparty.Ride;
import com.example.jffalat.poolparty.R;

import java.util.List;

/**
 * this class populates a ListView with a List<Ride>, and extends the ArrayAdapter<> class
 */
public class ListRideAdapter extends ArrayAdapter<Ride> {

    private Context context;
    private int layoutResourceId;
    private List<Ride> Rides = null;

    /**
     * the constructor of ListRideAdaptor
     * @param context the context of the Activity that initialized the new ListRideAdaptor
     * @param layoutResourceId the Layout that the newly generated View will manifest as
     * @param data the List<Ride> of messages to populate a View with
     */
    public ListRideAdapter(Context context, int layoutResourceId, List<Ride> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.Rides = data;
    }

    /**
     *
     * @param position the index of the position in the List
     * @param convertView the specific element to populate with the message data from the List<Ride> data
     * @param parent the View in which the newly created element will be embedded
     * @return the newly created and generated View
     */
    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MessageHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new MessageHolder();

            //TODO set the holder view id's
            holder.date = (TextView) row.findViewById(R.id.date);
            holder.addresses = (TextView) row.findViewById(R.id.addresses);
            holder.driver = (TextView) row.findViewById(R.id.driver);

            row.setTag(holder);
        } else {
            holder = (MessageHolder) row.getTag();
        }

        //get the current position from the list
        Ride ride = Rides.get(position);

        //TODO set the text for the row
        holder.date.setText(ride.get_start_time());
        holder.addresses.setText(ride.get_start_address() + " to " + ride.get_end_address());
        holder.driver.setText(ride.get_driver().get_email());

        return row;
    }

    /**
     * A class to represent the fields in the row layout
     */
    static class MessageHolder
    {
        TextView date;
        TextView addresses;
        TextView driver;
    }
}