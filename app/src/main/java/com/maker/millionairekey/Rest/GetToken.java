package com.maker.millionairekey.Rest;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class GetToken {

    Activity activity;

    public GetToken(Activity splashActivity) {
        this.activity = splashActivity;
    }

    public String mGetMyPref() {
        SharedPreferences prefs =  activity.getSharedPreferences("myPref", MODE_PRIVATE);
        return  prefs.getString("ACCESSTOKEN", null);
    }
}
