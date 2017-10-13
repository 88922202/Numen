package com.day.numen.service.task;

import android.util.Log;

/**
 * Created by work on 17-9-28.
 */

public class ReportTaskImpl implements ReportTask {
    @Override
    public void sendMessage(String consumer, String payload) {
        Log.d("Report", "consumer: " + consumer + ", payload: " + payload);
    }
}
