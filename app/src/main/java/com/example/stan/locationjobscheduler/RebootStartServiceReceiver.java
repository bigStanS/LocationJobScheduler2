package com.example.stan.locationjobscheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class RebootStartServiceReceiver extends BroadcastReceiver {
    private static final String TAG = "RebootServiceReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: ");
        Scheduler.scheduleJob(context);
    }


}
