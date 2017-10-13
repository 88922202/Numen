package com.day.numen.service.task;

import android.util.Log;

import com.day.numen.SmsManager;

/**
 * Created by work on 17-9-28.
 */

public class SMSTaskImpl implements ReportTask {

    SmsManager sms = new SmsManager();

    @Override
    public void sendMessage(String consumer, String payload) {
        Log.d("SMS", "consumer: " + consumer + ", payload: " + payload);
        sms.sendMsm(consumer, payload);
    }
}
