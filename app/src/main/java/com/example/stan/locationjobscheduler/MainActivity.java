package com.example.stan.locationjobscheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 3.3. Trigger the boot completed event from the command line
 ~/Android/Sdk/platform-tools$ ./adb root
 ~/Android/Sdk/platform-tools$ ./adb shell am broadcast -a android.intent.action.BOOT_COMPLETED

 am broadcast -a android.intent.action.ACTION_BOOT_COMPLETED

 C:\Users\Stan\AppData\Local\Android\sdk\platform-tools\adb.exe shell am broadcast -a android.intent.action.ACTION_BOOT_COMPLETED

 */
public class MainActivity extends AppCompatActivity {

    TextView output;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (TextView) findViewById(R.id.output);

        // Register the Broadcast Receiver
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver, new IntentFilter("JOB_SERVICE_MESSAGE"));
        // don't forget to unregisterReceiver the Broadcast Receiver

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // UnRegister the Broadcast Receiver
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mBroadcastReceiver);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Don't forget to register this receiver !!!!!
            // Nothing to add to manifest.
            Log.i(TAG, "onReceive: a message was received");
            Log.i(TAG, "onReceive: intent.getAction() = " + intent.getAction());
            String message = "a message was received";

//            switch (intent.getAction()) {
//                case NETWORK_SERVICE_MESSAGE:
//                    Log.i(TAG, "onReceive: Received data from our service.");
//                    // print the results from the network service with time-stamp
//                    SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
//                    String time = localDateFormat.format(new Date());
//                    message = intent.getStringExtra(NETWORK_SERVICE_PAYLOAD);
//                    output.append(message + " - " + time + "\n");
//                    break;
//            }

            output.setText(message);
        }
    };

}
