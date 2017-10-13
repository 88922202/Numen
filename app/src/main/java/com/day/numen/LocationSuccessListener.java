package com.day.numen;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.day.numen.common.LogUtils;
import com.day.numen.service.TaskService;
import com.day.numen.settings.Settings;
import com.day.numen.settings.SettingsManager;

/**
 * Created by wangzhe on 28/9/2017.
 */

public class LocationSuccessListener implements AMapLocationListener {
    private String mIncoming;

    public LocationSuccessListener(String incoming) {
        mIncoming = incoming;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        LogUtils.i("onLocationChanged. " + aMapLocation.toString());

        double latitude = aMapLocation.getLatitude();
        double longitude = aMapLocation.getLongitude();
        if (latitude == 0.0 && longitude == 0.0) {
            return;
        }

        String address = aMapLocation.getAddress();
        String message = "" + address + ",经纬:" + latitude + "," + longitude;
        if (SettingsManager.getInstance().canSendSms(mIncoming)) {
            TaskService.sendMessage(MyApplication.getInstance(), SettingsManager.getInstance().getTaskType(), mIncoming, message);
        }
    }
}
