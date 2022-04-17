package com.example.jffalat.poolparty;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sulli on 11/20/2017.
 */

/**
 * an object that holds all the ride information from logistics to passengers. It implements parcelable so that it can more easily be passed as a variable
 * between activities, and can be converted into and from a Parcel object. T
 */
public class Ride implements Parcelable {

//TODO work out how to use Java.util.Date and implement that for the date rather than a string

    private int capacity;
    private ArrayList<AccountData> rider_list;
    private AccountData driver;
    private String start_date;
    private String start_address;
    private String end_address;
    private int ride_id;
    private int max_distance;
    //private int metaData;

    /**
     * The default constructor of a ride object
     * @param driver an AccoutData of the driver
     * @param rider_list an arraylist of AccountData or the passengers
     * @param start_date a String of the ride departure time
     * @param start_address a String of the departure location
     * @param end_address a String of the destination address
     * @param capacity an int of the available number of seats
     * @param ride_id the rides personalized id used for identification on the server
     */
    public Ride(AccountData driver, ArrayList<AccountData> rider_list, String start_date, String start_address, String end_address, int capacity, int ride_id) {
        this.driver = driver;
        this.rider_list = rider_list;
        this.start_date=start_date;
        this.start_address = start_address;
        this.end_address = end_address;
        this.capacity=capacity;
        max_distance = 0;
        this.ride_id = ride_id;
    }

    /**
     * an overloaded constructor of a ride object
     * initializes an empty arraylist for rider_list
     * @param driver an AccoutData of the driver
     * @param start_date a String of the ride departure time
     * @param start_address a String of the departure location
     * @param end_address a String of the destination address
     * @param capacity an int of the available number of seats
     * @param ride_id the rides personalized id used for identification on the server
     */
    public Ride(AccountData driver, String start_date, String start_address, String end_address, int capacity, int ride_id) {
        this.driver = driver;
        this.rider_list = new ArrayList();
        this.start_date=start_date;
        this.start_address = start_address;
        this.end_address = end_address;
        this.capacity=capacity;
        max_distance = 0;
        this.ride_id = ride_id;

    }

    /**
     * an overloaded constructor of the ride object that takes a JSON object representation of a Ride object
     * @param obj a JSON representation of a Ride Object
     * @throws JSONException this will be thrown if the JSONObject is not formatted correctly
     */
    public Ride(JSONObject obj) throws JSONException {
        this.driver = (AccountData)obj.get("driver");

        //the following code is used to fill the rider_list with riders from the JSON (because there is no built in way to cast jsonArray to a list)
        rider_list = new ArrayList();
        JSONArray riders = obj.getJSONArray("riders");
        JSONObject rider;
        for(int count = 0; count < riders.length(); count++)
        {
            rider = riders.getJSONObject(count);
            rider_list.add(new AccountData(rider.getString("id"),rider.getString("email")));
        }

        this.start_date = obj.getString("start_date");
        this.start_address = obj.getString("start_address");
        this.end_address = obj.getString("end_address");
        this.capacity = obj.getInt("capacity");
        this.ride_id = obj.getInt("ride_ID");
        max_distance = 0;
    }

    /**
     * an alternative constructor or Ride object that takes a JSON formatted String to be casted as a Ride
     * @param str a String representation of a ride in JSON format
     * @throws JSONException if the String is not proper JSON format or if the JSON if not formatted correctly
     */
    public Ride(String str) throws JSONException {

        JSONObject obj = new JSONObject(str);
        this.driver = (AccountData)obj.get("driver");

        //the following code is used to fill the rider_list with riders from the JSON (because there is no built in way to cast jsonArray to a list)
        rider_list = new ArrayList();
        JSONArray riders = obj.getJSONArray("riders");
        JSONObject rider;
        for(int count = 0; count < riders.length(); count++)
        {
            rider = riders.getJSONObject(count);
            rider_list.add(new AccountData(rider.getString("id"),rider.getString("email")));
        }

        this.start_date = obj.getString("start_date");
        this.start_address = obj.getString("start_address");
        this.end_address = obj.getString("end_address");
        this.capacity = obj.getInt("capacity");
        this.ride_id = obj.getInt("ride_ID");
        max_distance = 0;
    }

    public ArrayList<AccountData> get_riders(){
        return rider_list;
    }

    public AccountData get_driver(){
        return driver;
    }

    public AccountData get_rider_at(int index) throws IndexOutOfBoundsException{
        return rider_list.get(index);
    }

    public int get_total_capacity(){
        return capacity;
    }
    public int get_available_seating(){
        return capacity - rider_list.size();
    }
    public String get_start_time(){
        return start_date;
    }
    public String get_start_address(){
        return start_address;
    }
    public String get_end_address(){
        return end_address;
    }
    public int get_ride_id(){
        return ride_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * overridden for parcelable writes the data of the ride object into the parcel input
     * @param parcel the parcel the ride will be written to
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //parcel.writeInt(metaData);
        parcel.writeParcelable(driver,0);
        parcel.writeTypedList(rider_list);
        parcel.writeString(start_date);
        parcel.writeString(start_address);
        parcel.writeString(end_address);
        parcel.writeInt(ride_id);
        parcel.writeInt(capacity);
        parcel.writeInt(max_distance);
    }

    public static final Parcelable.Creator<Ride> CREATOR = new Parcelable.Creator<Ride>() {
        public Ride createFromParcel(Parcel in) {
            return new Ride(in);
        }

        public Ride[] newArray(int size) {
            return new Ride[size];
        }
    };

    /**
     * a private constructor of Ride object that takes a Parcel and constructs a ride from the parcel data
     * @param in the Parcel to be converted to a Ride object
     */
    private Ride(Parcel in) {
        driver = (AccountData) in.readParcelable(AccountData.class.getClassLoader());;
        rider_list = new ArrayList();
        in.readTypedList(rider_list,AccountData.CREATOR);
        start_date = in.readString();
        start_address = in.readString();
        end_address = in.readString();
        ride_id = in.readInt();
        capacity = in.readInt();
        max_distance = in.readInt();
    }

    @Override
    public String toString() {
        return "Driver: "+driver.toString()+ "\nRiders: " + rider_list.toString()+"\nCapacity: "+capacity+"\nStartDate: "+start_date+"\nStart Address: "+start_address+"\nEnd Address: "+end_address+"\nRide id: "+ride_id;
    }
}
