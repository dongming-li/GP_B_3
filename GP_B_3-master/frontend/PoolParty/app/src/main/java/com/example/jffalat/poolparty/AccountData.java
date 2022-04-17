package com.example.jffalat.poolparty;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sulli on 11/20/2017.
 */

public class AccountData implements Parcelable{
    private String id;
    private String email;

    /**
     * AccountData is a simplified version of the Account Class that does not include the more sensitive
     * information included in Account. This is the default constructor.
     * @param id this is the user's user_id
     * @param email this is the user's email address
     */
    public AccountData(String id, String email) {
        this.id = id;
        this.email = email;
    }

public AccountData(JSONObject json_data) {
    try {
        this.id = json_data.getString("id");
        this.email = json_data.getString("email");
    } catch (JSONException e) {
        e.printStackTrace();
    }
}
    /**AccountData is a simplified version of the Account Class that does not include the more sensitive
     * information included in Account. This is an overloaded constructor without fields. The user id and email
     * are set to the String "null" rather than a null value
     */
    public AccountData(){
        this.id = "null";
        this.email = "null";
    }
    public String get_id(){
        return id;
    }
    public String get_email(){
        return email;
    }
    public String get_rating(){
        return "0 ratings yet";
    }


    /**
     * This is a method from Parcelable interface. It serves no purpose in this class. It simply returns a 0 if called
     * @return 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * This is an overridden method from the Parcelable interface.
     * @param parcel the Parcel that the metadata is writen to
     * @param i this parameter appears in the interface but serves no purpose in this class
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //parcel.writeInt(metaData);
        parcel.writeString(id);
        parcel.writeString(email);
    }

    /**
     * This is the Parcelable.Creator<> that is uded to recreate a the object from a Parcel
     */
    public static final Parcelable.Creator<AccountData> CREATOR = new Parcelable.Creator<AccountData>() {
        /**
         * This is the method that generates a new AccountData object from a Parcel
         * @param in the Parcel that is to be converted into an new instance of an AccoutData object
         * @return the newly generated AccountData object
         */
        public AccountData createFromParcel(Parcel in) {
            //return new AccountData(in.readString(), in.readString());
            return new AccountData(in);
        }

        /**
         *
         * @param size int: the size of the array of AccountData objects to be returned
         * @return returns an empty array of length "size"
         */
        public AccountData[] newArray(int size) {
            return new AccountData[size];
        }
    };

    /**
     * A private constructor used by Parcelable.Creator<>.createFromParcel to create construct a new AccountData
     * object from a parcel
     * @param in the Parcel with all the information to make a new AccountData object
     */
    private AccountData(Parcel in) {
        id = in.readString();
        email = in.readString();
    }


    /**
     * Returns a string of the user's data in the form "[id: <id String>],[email: <email String>]"
     * @return a string of the user's data in the form "[id: <id String>],[email: <email String>]"
     */
    @Override
    public String toString() {
        return "[id: "+id+"],[email: "+email+"]";
    }
}
