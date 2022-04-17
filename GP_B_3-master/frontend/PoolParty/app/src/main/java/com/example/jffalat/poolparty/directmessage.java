package com.example.jffalat.poolparty;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sulli on 11/8/2017.
 * this class represents a single message to/from the user.
 */
public class directmessage {


    private String recipient_id;
    private String recipient_email;
    private String sender_id;
    private String sender_email;
    private String message_body;
    private String timestamp;

    /**The default constructor for a directmessage object
     *
     * @param recipient_id the id of the user who is intended to receive the message
     * @param recipient_email the email address of the user who is intended to receive the message
     * @param sender_id the id of the user who sent the message
     * @param sender_email the email of the user who sent the message
     * @param message_body the message text
     * @param timestamp the time that the message was received by the server. New messages that haven't been sent to the server should have the timestamp set to "unset"
     */
    public directmessage(String recipient_id, String recipient_email, String sender_id, String sender_email, String message_body, String timestamp) {
        this.recipient_id = recipient_id;
        this.recipient_email = recipient_email;
        this.sender_id = sender_id;
        this.sender_email = sender_email;
        this.message_body = message_body;
        this.timestamp =  timestamp;

    }

    /**
     * an overloaded constructor that instead takes a JSONObject (the JSONObject has to have the parameters "to","from","message" and "timestamp")
     * @param obj the JSONObject to be converted into a directmessage object
     * @throws JSONException throws if the parameter JSONObject cannot be converted into a directmessage objectt
     */
    public directmessage(JSONObject obj) throws JSONException {
        this.recipient_id = obj.getString("to");
        this.recipient_email = obj.getString("to");
        this.sender_id = obj.getString("from");
        this.sender_email = obj.getString("from");
        this.message_body = obj.getString("message");
        if(obj.getString("timestamp")!=null) {
            this.timestamp = obj.getString("timestamp");
        }
        else
        {
            this.timestamp = "unsent";
        }
    }

    /**
     * this is an overloaded constructor that takes in a String (the string must be in JSON syntax)
     * @param str a String in JSON syntax that can be converted into a JSON and then into a directmessage object
     * @throws JSONException throws if the String is not in proper format to be used in the JSONObject constructor
     *                          or if the JSONObject that is made cannot be converted into a directmessage object
     */
    public directmessage(String str) throws JSONException {

        JSONObject obj = new JSONObject(str);
        this.recipient_id = obj.getString("to");
        this.recipient_email = obj.getString("to");
        this.sender_id = obj.getString("from");
        this.sender_email = obj.getString("from");
        this.message_body = obj.getString("message");
        if(obj.getString("timestamp")!=null) {
            this.timestamp = obj.getString("timestamp");
        }
        else
        {
            this.timestamp = "unsent";
        }
    }

    /**
     * @return the message's recipient's user id
     */
    public String get_recipient_id() {
        return recipient_id;
    }

    /**
     * @return the message's recipient's email
     */
    public String get_recipient_email() {
        return recipient_email;
    }

    /**
     * @return the message's senders's user id
     */
    public String get_sender_id() {
        return sender_id;
    }

    /**
     * @return the message's senders's email
     */
    public String get_sender_email() {
        return sender_email;
    }

    /**
     * @return the message text
     */
    public String get_message_body() {
        return message_body;
    }

    /**
     * @return a string of the time that the server received the message(the timestamp)
     */
    public String get_timestamp() {
        return timestamp;
    }

}