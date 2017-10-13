package com.day.numen.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.day.numen.AmapLocationService;
import com.day.numen.LocationSuccessListener;
import com.day.numen.settings.SettingsManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by work on 17-9-27.
 */

public class NumenBroadcastReceiver extends BroadcastReceiver {

    private static final int TIMEOUT = 30 * 1000;       //30秒内主动挂断，不定位
    private static HashMap<String, String> map = new HashMap<>();

    private long mStartTime;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TEST_RECEIVER", intent.getAction() + "  ==  ");
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            if (SettingsManager.getInstance().isServiceOn()) {
                return;
            }else {
                System.exit(0);
            }
        }
        if (Intent.ACTION_NEW_OUTGOING_CALL.equals(intent.getAction())) {
            return;
        }

        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            if (tm == null) {
                return;
            }

            tm.listen(new PhoneListener(context), PhoneStateListener.LISTEN_CALL_STATE);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    class PhoneListener extends PhoneStateListener {

        WeakReference<Context> mContextRef;

        public PhoneListener(Context ctx) {
            this.mContextRef = new WeakReference<>(ctx);
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING: {
                        Log.e("TEST_RECEIVER", "incoming: " + incomingNumber);
                        if (SettingsManager.getInstance().isServiceOn()) {
                            boolean inEmergentList = SettingsManager.getInstance().inUrgentList(incomingNumber);
                            if (inEmergentList) {
                                if (!TextUtils.isEmpty(incomingNumber) && !"null".equalsIgnoreCase(incomingNumber)) {
                                    if (!map.containsKey(incomingNumber)) {
                                        map.put(incomingNumber, incomingNumber);
                                        mStartTime = System.currentTimeMillis();
                                    }
                                }
                            }
                        }

                        break;
                    }
                    case TelephonyManager.CALL_STATE_OFFHOOK: {
                        Log.e("TEST_RECEIVER", "offhook");
                        map.remove(incomingNumber);
                        break;
                    }
                    case TelephonyManager.CALL_STATE_IDLE: {
                        Log.e("TEST_RECEIVER", "idle");
                        Log.e("TEST_RECEIVER", "" + map.toString());

                        //30秒内挂断不定位
                        if (System.currentTimeMillis() - mStartTime >= TIMEOUT) {

                            //如果来电在紧急联系人列表，则启动定位
                            String incoming = map.remove(incomingNumber);
                            if (!TextUtils.isEmpty(incoming)) {
                                AmapLocationService.getInstance().setLocationSuccessListener(new LocationSuccessListener(incoming))
                                        .startLocation();
                            }

                            // 是否还有未处理的任务
                            ArrayList<String> temp = new ArrayList<>();
                            if (!map.isEmpty()) {
                                temp.addAll(map.keySet());
                            }
                            if (!temp.isEmpty()) {
                                for (String key : temp) {
                                    String item = map.remove(key);
                                    if (!TextUtils.isEmpty(item)) {
                                        AmapLocationService.getInstance().setLocationSuccessListener(new LocationSuccessListener(item))
                                                .startLocation();
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

}
