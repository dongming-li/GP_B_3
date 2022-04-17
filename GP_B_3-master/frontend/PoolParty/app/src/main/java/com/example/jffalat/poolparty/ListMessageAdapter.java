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

import com.example.jffalat.poolparty.Account;
import com.example.jffalat.poolparty.R;

import java.util.List;
/**
 * a class that populates a ListView with a List<directmessage>, and extends the ArrayAdapter<> class
 * used in the chat activity to desplay messages as individual objects in an array.
 */
public class ListMessageAdapter extends ArrayAdapter<directmessage> {

    private Context context;
    private int layoutResourceId;
    private List<directmessage> Messages = null;

    /**
     * the constructor of ListMessageAdaptor
     * @param context the context of the Activity that initialized the new ListMessageAdaptor
     * @param layoutResourceId the Layout that the newly generated View will manefest as
     * @param data the List<directmessage> of messages to populate a View with
     */
    public ListMessageAdapter(Context context, int layoutResourceId, List<directmessage> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.Messages = data;
    }

    /**
     *
     * @param position the position
     * @param convertView the specific element to populate with the message data from the List<directmessage> data
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
            holder.sender_email = (TextView) row.findViewById(R.id.sender_email);
            holder.message_text = (TextView) row.findViewById(R.id.message_text);
            holder.timestamp = (TextView) row.findViewById(R.id.time_stamp);

            row.setTag(holder);
        } else {
            holder = (MessageHolder) row.getTag();
        }

        //get the current position from the list
        directmessage message = Messages.get(position);

        //TODO set the text for the row
        holder.sender_email.setText(message.get_sender_email());
        holder.message_text.setText(message.get_message_body());
        holder.timestamp.setText(message.get_timestamp());

        return row;
    }

    /**
     * A class to represent the fields in the row layout
     */
    static class MessageHolder
    {
        TextView sender_email;
        TextView message_text;
        TextView timestamp;
    }
}