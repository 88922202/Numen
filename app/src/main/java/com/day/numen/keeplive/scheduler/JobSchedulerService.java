package com.day.numen.keeplive.scheduler;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.day.numen.keeplive.KeepLiveManager;

/**
 * Created by work on 17-9-27.
 * <p>
 * Job task service
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message != null && message.obj != null) {
                jobFinished((JobParameters) message.obj, true);
            }
            return true;
        }
    });

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("JSS", "=========onStartJob=================");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                KeepLiveManager.startJobScheduler(getApplicationContext());
            }
        }, 5000);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("JSS", "=========onStopJob=================");
        return false;
    }
}
