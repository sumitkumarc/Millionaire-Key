package com.maker.millionairekey.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class InstallReferrerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            if (intent == null) {
                return;
            }

            String referrerId = intent.getStringExtra("referrer");
            Toast.makeText(context, "" + referrerId, Toast.LENGTH_SHORT).show();
            if (referrerId == null) {
                return;
            }
        } catch (Exception e) {
        }
    }
}
