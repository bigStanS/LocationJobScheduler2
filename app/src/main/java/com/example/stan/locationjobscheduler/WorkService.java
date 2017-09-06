package com.example.stan.locationjobscheduler;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class WorkService extends IntentService {

    public WorkService() {
        super("WorkService");
    }



    @Override
    protected void onHandleIntent( Intent intent) {
        Intent messageIntent = new Intent("JOB_SERVICE_MESSAGE"); // the action
        messageIntent.putExtra("JOB_SERVICE_PAYLOAD", " some kind of data");  // the extra
        LocalBroadcastManager LBM = LocalBroadcastManager.getInstance(getApplicationContext());    // Get reference to the LocalBroadcastManager
        LBM.sendBroadcast(messageIntent);

    }
}
