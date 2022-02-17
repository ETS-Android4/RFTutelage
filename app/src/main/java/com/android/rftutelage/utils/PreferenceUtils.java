package com.android.rftutelage.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

public class PreferenceUtils {

    public PreferenceUtils(){

    }

    public static boolean saveEmail(String email, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_EMAIL,email);
        prefsEditor.apply();

        return true;
    }

    public static String getEmail(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_EMAIL,null);
    }

    public static boolean saveRollNumber(String roll_no, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_ROLLNO,roll_no);
        prefsEditor.apply();
        return true;
    }

    public static String getRollNumber(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_ROLLNO,null);
    }

    public static boolean savecurrentSemester(String semester, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("semester",semester);
        prefsEditor.apply();
        return true;
    }

    public static String getcurrentSemester(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("semester",null);
    }

    public static boolean savesubjectcode(String subjectcode, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("subjectcode",subjectcode);
        prefsEditor.apply();
        return true;
    }

    public static String getsubjectcode(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("subjectcode",null);
    }

    public static boolean savepapercode(String papercode, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("papercode",papercode);
        prefsEditor.apply();
        return true;
    }

    public static String getpapercode(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("papercode",null);
    }

    public static boolean saveselectedsemester(String selectedsemester, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("selectedsemester",selectedsemester);
        prefsEditor.apply();
        return true;
    }

    public static String getselectedsemester(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("selectedsemester",null);
    }

    public static boolean savefaculty1_code(String faculty1_code, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("faculty1_code",faculty1_code);
        prefsEditor.apply();
        return true;
    }

    public static String getfaculty1_code(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("faculty1_code",null);
    }

    public static boolean savefaculty2_code(String faculty2_code, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("faculty2_code",faculty2_code);
        prefsEditor.apply();
        return true;
    }

    public static String getfaculty2_code(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("faculty2_code",null);
    }

    public static boolean savefaculty3_code(String faculty3_code, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("faculty3_code",faculty3_code);
        prefsEditor.apply();
        return true;
    }

    public static String getfaculty3_code(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("faculty3_code",null);
    }

}
