package com.example.stan.locationjobscheduler;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class WorkService extends IntentService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = "WorkService";
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    public WorkService() {
        super("WorkService");
    }

    @SuppressWarnings("MissingPermission")  // We asked for permission at logon.
    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i(TAG, "onHandleIntent: Entry ");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(30 * 1000)        // 30 seconds, in milliseconds
                .setFastestInterval(60 * 1000); // 1 minute, in milliseconds.

        if (!mGoogleApiClient.isConnected()) {
            Log.i(TAG, "onHandleIntent: mGoogleApiClient is NOT Connected ... Connecting ......");
            mGoogleApiClient.connect();
        } else {
            Log.i(TAG, "onHandleIntent: mGoogleApiClient isConnected.");
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            } else {
                updateUI(location.toString());
            }
        }

        Log.i(TAG, "onHandleIntent: Exit");
    }

    @SuppressWarnings("MissingPermission")  // We asked for permission at logon.
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected: ");
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            String latitude = String.valueOf(location.getLatitude());
            String longitude = String.valueOf(location.getLongitude());
            updateUI("Lon: " + longitude + "  lat: " + latitude);
            updateServer(longitude, latitude);
        } else {
            updateUI("Location not available");
        }
        Log.i(TAG, "onConnected: getLastLocation = " + location);
    }

    private void updateUI(String location) {
        Intent messageIntent = new Intent("JOB_SERVICE_MESSAGE"); // the action
        messageIntent.putExtra("JOB_SERVICE_PAYLOAD", location.toString());  // the extra
        LocalBroadcastManager LBM = LocalBroadcastManager.getInstance(getApplicationContext());    // Get reference to the LocalBroadcastManager
        LBM.sendBroadcast(messageIntent);
    }

    public void updateServer(String longitude, String latitude) {

//        // the parameters
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("trucker", pref.getString("trucker_id", "")));
//        params.add(new BasicNameValuePair("longitude", String.valueOf(location.getLongitude())));
//        params.add(new BasicNameValuePair("latitude", String.valueOf(location.getLatitude())));
//        // Send to server
//        AsyncRequest putRequest = new AsyncRequest(null, "PUT", params);
//        putRequest.execute(preUrl + "tupdateLocation");   // send PUT loadStatus command. eg. http://192.168.1.190:8080/loadStatus

////        String url = "http://192.168.1.190:8080/tavailable";
////        String jsonParams = "{\"trucker\":\"3b7142a1d4e64f750c4af0a91387adc0d13e1c63\"," +
////                "\"available\":\"online\"" +
////                "}";

        Log.i(TAG, "updateServer: Entry");
        String url = "https://getmovez.com/tupdateLocation";
//        String url = "http://192.168.1.190:8080/tupdateLocation";
        String jsonParams = "{\"trucker\":\"3b7142a1d4e64f750c4af0a91387adc0d13e1c63\"," +
//        String jsonParams = "{\"trucker\":\"a238ed0ed7556555b789a6f10b9e2e46232c21cf\"," +
                "\"longitude\":\"" + longitude + "\"," +
                "\"latitude\":\"" + latitude + "\"" +
                "}";

        Intent intent = new Intent(this, HttpService.class);
        intent.putExtra("THE_URL", url);
        intent.putExtra("THE_METHOD", "PUT");
        intent.putExtra("THE_PAYLOAD", jsonParams);
        startService(intent);
        Log.i(TAG, "updateServer: Exit");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended: ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed: ");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChanged: ");
    }
}
