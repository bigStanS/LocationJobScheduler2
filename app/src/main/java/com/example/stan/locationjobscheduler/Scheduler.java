package com.example.stan.locationjobscheduler;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import static android.content.Context.JOB_SCHEDULER_SERVICE;


public class Scheduler {

    // schedule the start of the service every 10 - 30 seconds
    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, TheJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(1 * 60 * 1000); // wait at least
        builder.setOverrideDeadline(10 * 60 * 1000); // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }

}
