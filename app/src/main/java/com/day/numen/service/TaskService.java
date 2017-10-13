package com.day.numen.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.day.numen.service.task.EmailTaskImpl;
import com.day.numen.service.task.ReportTask;
import com.day.numen.service.task.ReportTaskImpl;
import com.day.numen.service.task.SMSTaskImpl;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by work on 17-9-28.
 */

public class TaskService extends Service {
    private static final String TAG = TaskService.class.getSimpleName();

    public static final String EXTRA_TYPE = "extra.type";
    public static final String EXTRA_CONSUMER = "extra.consumer";
    public static final String EXTRA_PAYLOAD = "extra.payload";
    public static final String EXTRA_TIMESTAMP = "extra.timestamp";

    /**
     * @param ctx      Context
     * @param type     [sms] [email] [report]
     * @param consumer 目标
     * @param payload  数据
     */
    public static void sendMessage(Context ctx, String type, String consumer, String payload) {
        Intent intent = new Intent(ctx, TaskService.class);
        intent.putExtra(EXTRA_TYPE, TextUtils.isEmpty(type) ? "" : type);
        intent.putExtra(EXTRA_CONSUMER, consumer);
        intent.putExtra(EXTRA_PAYLOAD, payload);
        intent.putExtra(EXTRA_TIMESTAMP, System.currentTimeMillis());
        ctx.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init(intent);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void init(Intent intent) {
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }
        String type = bundle.getString(EXTRA_TYPE, "");
        if (TextUtils.isEmpty(type)) {
            return;
        }
        String consumer = bundle.getString(EXTRA_CONSUMER, "");
        String payload = bundle.getString(EXTRA_PAYLOAD, "");
        long timestamp = bundle.getLong(EXTRA_TIMESTAMP, 0);
        if (timestamp <= 0) {
            timestamp = System.currentTimeMillis();
        }
        String time = new SimpleDateFormat("MMddHHmmZ", Locale.getDefault()).format(timestamp);
        Log.d(TAG, "type: " + type + ", consumer: " + consumer + ", time: " + time + ", payload: " + payload);

        ReportTask task = null;
        switch (type) {
            case "sms": {
                task = new SMSTaskImpl();
                break;
            }
            case "email": {
                task = new EmailTaskImpl();
                break;
            }
            case "report": {
                task = new ReportTaskImpl();
                break;
            }
            default: {
                Log.d(TAG, "unsupported type: " + type);
                break;
            }
        }
        if (task != null) {
            Log.d(TAG, "task: " + task.getClass().getName());
            task.sendMessage(consumer, payload + "-" + time);
        }
    }
}
