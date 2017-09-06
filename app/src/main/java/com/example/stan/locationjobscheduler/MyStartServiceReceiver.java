package com.example.stan.locationjobscheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class MyStartServiceReceiver extends BroadcastReceiver {
    private static final String TAG = "MyStartServiceReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: ");
        Util.scheduleJob(context);
    }


}
