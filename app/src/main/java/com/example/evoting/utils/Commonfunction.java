package com.example.evoting.utils;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Commonfunction {


    public static boolean checkString(String s) {

        if (s != null && !s.equalsIgnoreCase("null") && s.length() > 0) {
            return true;
        }
        else {
            return false;
        }


    }

    public static boolean checkMobileNo(String s) {

        if (s != null && !s.equalsIgnoreCase("null") && s.length() == 10) {
            return true;
        }
        else {
            return false;
        }


    }

    public static boolean checkEnrollno(String s) {

        if (s != null && !s.equalsIgnoreCase("null") && s.length() == 12) {
            return true;
        }
        else {
            return false;
        }


    }

    public static boolean checkPassword(String s) {

        if (s != null && !s.equalsIgnoreCase("null") && s.length() >= 6) {
            return true;
        }
        else {
            return false;
        }


    }

    public static boolean checkEmail(String s) {

        if (s != null && !s.equalsIgnoreCase("null") && s.length() > 0 && Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(),s)) {
            return true;
        }
        else {
            return false;
        }


    }
    public static boolean checkVerification(String s) {

        if (s != null && !s.equalsIgnoreCase("null") && s.length() == 4) {
            return true;
        } else {
            return false;
        }
    }

}
