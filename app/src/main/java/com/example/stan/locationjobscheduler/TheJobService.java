package com.example.stan.locationjobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

public class TheJobService extends JobService {
    private static final String TAG = "TheJobService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: Entry");
        Intent service = new Intent(getApplicationContext(),WorkService.class);
        getApplicationContext().startService(service);

        // now reschedule the job
        Scheduler.scheduleJob(getApplicationContext());
        Log.i(TAG, "onStartJob: Exit");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

}
