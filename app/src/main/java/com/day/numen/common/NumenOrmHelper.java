package com.day.numen.common;

import android.content.Context;

import com.day.numen.MyApplication;
import com.day.numen.settings.Settings;
import com.day.numen.urgent.model.UrgentContact;

import org.cn.orm.SimpleOrmHelper;

public class NumenOrmHelper {

    public static final String DATABASE_NAME = "test.db";

    private static SimpleOrmHelper mOrmHelper;
    private static final Object lock = new Object();

    public static SimpleOrmHelper getInstance() {
        if (mOrmHelper == null) {
            synchronized (lock) {
                if (mOrmHelper == null) {
                    init(MyApplication.getInstance());
                }
            }
        }
        return mOrmHelper;
    }

    public static void init(Context ctx) {
        SimpleOrmHelper.Builder builder = SimpleOrmHelper.create()
                .addAnnotatedClass(UrgentContact.class)
                .addAnnotatedClass(Settings.class);
        mOrmHelper = builder.build(ctx, DATABASE_NAME, 1);
    }
}
