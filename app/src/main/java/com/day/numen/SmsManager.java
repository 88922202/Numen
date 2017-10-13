package com.day.numen;

import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

public class SmsManager {

    private String mMessage;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public void sendMsm(String phone){
        android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
        sms.sendTextMessage(phone, null, mMessage, null, null);
    }

    public void sendMsm(String phone, String message){
        android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
        List<String> divide = sms.divideMessage(message);
        for (String content : divide){
            sms.sendTextMessage(phone, null, content, null, null);
        }
    }
}
