package com.example.stan.locationjobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

public class TestJobService extends JobService {
    private static final String TAG = "TestJobService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: Entry");
        Intent service = new Intent(getApplicationContext(),WorkService.class);
        getApplicationContext().startService(service);
        Util.scheduleJob(getApplicationContext()); // reschedule the job
        Log.i(TAG, "onStartJob: Exit");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

}
