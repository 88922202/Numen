package com.day.numen;

import android.app.Application;

import com.day.numen.common.NumenOrmHelper;

/**
 * Created by wangzhe on 27/9/2017.
 */

public class MyApplication extends Application {

    private static MyApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        NumenOrmHelper.init(getApplicationContext());
    }

    public static MyApplication getInstance() {
        return INSTANCE;
    }
}
