package com.day.numen.keeplive;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;
import android.util.Log;

import com.day.numen.keeplive.scheduler.JobSchedulerService;

/**
 * Created by work on 17-9-27.
 */

public class KeepLiveManager {

//    public static void startSyncAccount() {
//        SyncUtil.requestSync();
//    }

    public static void startJobScheduler(Context ctx) {
        JobScheduler mJobScheduler = (JobScheduler) ctx.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(ctx.getPackageName(), JobSchedulerService.class.getName()));

        builder.setPersisted(true);
        builder.setRequiresDeviceIdle(true);
        builder.setOverrideDeadline(5000);
//        builder.setPeriodic(3000);
//        builder.setMinimumLatency(5000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);

        PersistableBundle bundle = new PersistableBundle();
        bundle.putString("KEY", "VALUE");
        builder.setExtras(bundle);

        if (mJobScheduler != null) {
            int result = mJobScheduler.schedule(builder.build());
            Log.d("KLM", "schedule task: " + result);
        }
    }

}
