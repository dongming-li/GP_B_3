package com.example.jffalat.poolparty;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jffalat on 11/5/2017.
 */

/**
 * This class represents a user and contains all the data the can be accessed by an admin. Implements Serializable to help with passing it between activities
 */
public class Account implements Serializable{


    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String accountType;
    private boolean is_rider;
    private boolean is_driver;
    private boolean is_admin;

    /**
     *
     * @param firstname
     * @param lastname
     * @param email
     * @param password
     * @param is_rider
     * @param is_driver
     * @param is_admin
     */
    public Account(String firstname, String lastname, String email, String password, boolean is_rider, boolean is_driver, boolean is_admin) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.is_driver = is_driver;
        this.is_rider = is_rider;
        this.is_admin = is_admin;
    }

    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public boolean getDriver() { return is_driver; }
    public boolean getRider() { return is_rider; }
    public boolean getAdmin() { return is_admin; }

    /**
     * returns an int to represent a user as an int
     * @return 4: rider & driver & admin, 3: driver & rider, 2: driver, 1: rider, 0: admin
     */
    public int getAccountType() {
        if (getDriver() && getRider() && getAdmin()) {
            return 4;
        }
        else if (getDriver() && getRider() && !getAdmin()) {
            return 3;
        }
        else if (getDriver() && !getRider() && !getAdmin()) {
            return 2;
        }
        else if (!getDriver() && getRider() && !getAdmin()) {
            return 1;
        }
        else if (!getDriver() && !getRider() && getAdmin()) {
            return 0;
        }
        return 5;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setEmail(String email) {this.email = email;  }
    public void setPassword(String password) {this.password = password; }
    public void setDriver(boolean is_driver) {
        this.is_driver = is_driver;
    }
    public void setRider(boolean is_rider) {
        this.is_rider = is_rider;
    }
    public void setAdmin(boolean is_admin) {
        this.is_admin = is_admin;
    }
    public void setAccountType(String accountType) { this.accountType = accountType; }
}