package com.example.evoting.utils;

import android.content.SharedPreferences;
import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by AthulyaTech on 28-08-2017.
 */

public class AllSharedPrefernces {
    String CustomerNo;
    String seletedCity;
    String UserType;
    String device_id;
    boolean isUserLogin=false;
    Context c;
    String customerData = "";




    public String getDevice_id() {
        SharedPreferences prefs = c.getSharedPreferences("MyPreff", MODE_PRIVATE);
        this.device_id = prefs.getString("device_id", null);
        return device_id;
    }

    public void setDevice_id(String device_id) {
        SharedPreferences.Editor editor = c.getSharedPreferences("MyPreff", MODE_PRIVATE).edit();
        this.device_id = device_id;
        editor.putString("device_id", device_id);
        editor.apply();
        this.device_id = device_id;
    }

    public AllSharedPrefernces(Context context, SharedPreferences s) {
        this.c = context;
        s = context.getSharedPreferences("MyPreff", MODE_PRIVATE);
    }


    public AllSharedPrefernces(Context context) {
        this.c = context;
    }

    public String getCustomerNo() {
        SharedPreferences prefs = c.getSharedPreferences("MyPreff", MODE_PRIVATE);
        this.CustomerNo = prefs.getString("CustomerNo", "");
        return CustomerNo;
    }

    public void setCustomerNo(String customerNo) {
        SharedPreferences.Editor editor = c.getSharedPreferences("MyPreff", MODE_PRIVATE).edit();
        this.CustomerNo = customerNo;
        editor.putString("CustomerNo", CustomerNo);
        editor.apply();
    }



    public String getSeletedCity() {
        SharedPreferences prefs = c.getSharedPreferences("MyPreff", MODE_PRIVATE);
        this.seletedCity = prefs.getString("seletedCity", "");
        return seletedCity;
    }

    public void setSeletedCity(String preferArea) {
        this.seletedCity = preferArea;
        SharedPreferences.Editor editor = c.getSharedPreferences("MyPreff", MODE_PRIVATE).edit();
        editor.putString("seletedCity", preferArea);
        editor.apply();
    }


    public boolean isUserLogin() {
        SharedPreferences prefs = c.getSharedPreferences("MyPreff", MODE_PRIVATE);
        this.isUserLogin = prefs.getBoolean("isUsserLogin", false);

        return isUserLogin;
    }

    public void setUserLogin(boolean userLogin) {
        isUserLogin = userLogin;
        SharedPreferences.Editor editor = c.getSharedPreferences("MyPreff", MODE_PRIVATE).edit();
        editor.putBoolean("isUsserLogin", isUserLogin);
        editor.apply();
    }



    public String getCustomerData() {

        SharedPreferences prefs = c.getSharedPreferences("MyPreff", MODE_PRIVATE);
        this.customerData = prefs.getString("customerData", "");
        return customerData;
    }

    public void setCustomerData(String customerData) {
        SharedPreferences.Editor editor = c.getSharedPreferences("MyPreff", MODE_PRIVATE).edit();
        this.customerData = customerData;
        editor.putString("customerData", customerData);
        editor.apply();
    }


    public String getUserType() {

        SharedPreferences prefs = c.getSharedPreferences("MyPreff", MODE_PRIVATE);
        this.UserType = prefs.getString("UserType", "");
        return UserType;
    }

    public void setUserType(String customerData) {
        SharedPreferences.Editor editor = c.getSharedPreferences("MyPreff", MODE_PRIVATE).edit();
        this.UserType = customerData;
        editor.putString("UserType", customerData);
        editor.apply();
    }

    public void ClearAllData() {
        SharedPreferences.Editor editor = c.getSharedPreferences("MyPreff", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }


}
