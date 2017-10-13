package com.day.numen.settings;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.day.numen.MyApplication;
import com.day.numen.common.NumenOrmHelper;
import com.day.numen.urgent.model.UrgentContact;

import org.cn.orm.SimpleOrmHelper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wangzhe on 28/9/2017.
 */

public class SettingsManager {

    private static final String PREFERENCE = "preference";
    private static final String SERVICE_STATE = "service_state";
    private static final String UPLOAD_TYPE = "upload_type";
    private static final String FIRST_RUN = "first_run";

    private static SettingsManager mInstance;
    private SharedPreferences mSharedPreferences;

    private SimpleOrmHelper mOrmHelper;

    private SettingsManager() {
        mOrmHelper = NumenOrmHelper.getInstance();
        mSharedPreferences = MyApplication.getInstance().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
    }

    synchronized public static SettingsManager getInstance() {
        if (mInstance == null) {
            mInstance = new SettingsManager();
        }

        return mInstance;
    }

    //来电是否在用户设置的紧急联系人列表中
    public boolean inUrgentList(String incoming) {
        return mOrmHelper.get(UrgentContact.class, incoming) != null;
    }

    //是否可以发送短信
    public boolean canSendSms(String incoming) {
        return SettingsManager.getInstance().getUploadType("sms");
    }

    //用户开启或关闭服务
    public void setServiceState(boolean state){
        mSharedPreferences.edit().putBoolean(SERVICE_STATE, state).apply();
    }

    //用户是否开启了本服务
    public boolean isServiceOn(){
        return mSharedPreferences.getBoolean(SERVICE_STATE, false);
    }


    //登录密码是否正确
    public boolean isPasswordRight(String password){
        if (TextUtils.isEmpty(password)){
            return false;
        }
        return password.equals(getPassword());
    }

    //用户是否设置了登录密码
    public boolean havePassword(){
        return !TextUtils.isEmpty(getPassword());
    }

    //保存用户密码
    public void saveOrUpdatePassword(String password){
        Settings obj = (Settings) mOrmHelper.get(Settings.class, Settings.ID_PASSWORD);
        if (obj == null) {
            mOrmHelper.save(new Settings(Settings.ID_PASSWORD, password));
        } else {
            mOrmHelper.update(new Settings(Settings.ID_PASSWORD, password));
        }
    }

    private String getPassword(){
        Settings obj = (Settings) mOrmHelper.get(Settings.class, Settings.ID_PASSWORD);
        if (obj != null) {
            return obj.value;
        }
        return null;
    }

    //保存数据上报方式
    public void saveOrUpdateTaskType(String value){
        Settings obj = (Settings) mOrmHelper.get(Settings.class, Settings.ID_TASK_TYPE);
        if (obj == null) {
            mOrmHelper.save(new Settings(Settings.ID_TASK_TYPE, value));
        } else {
            mOrmHelper.update(new Settings(Settings.ID_TASK_TYPE, value));
        }
    }

    public void setUploadType(String type, boolean checked){
        Set<String> uploadTypes = mSharedPreferences.getStringSet(UPLOAD_TYPE, null);
        if (uploadTypes == null){
            uploadTypes = new HashSet<>();
        }
        if (checked){
            uploadTypes.add(type);
        }else {
            uploadTypes.remove(type);
        }

        mSharedPreferences.edit().putStringSet(UPLOAD_TYPE, uploadTypes).apply();
    }

    public boolean getUploadType(String type){
        Set<String> uploadTypes = mSharedPreferences.getStringSet(UPLOAD_TYPE, null);
        boolean isExists = false;
        if (uploadTypes != null) {
            Iterator iterator = uploadTypes.iterator();
            while (iterator.hasNext()) {
                String each = (String) iterator.next();
                if (type.equals(each)) {
                    isExists = true;
                }
            }
        }else {
            //默认选中
            isExists = true;
        }

        return isExists;
    }

    public String getTaskType() {
        Settings obj = (Settings) mOrmHelper.get(Settings.class, Settings.ID_TASK_TYPE);
        if (obj != null) {
            return obj.value;
        }
        return "sms";
    }

    public void setNotFirstRun(){
        mSharedPreferences.edit().putBoolean(FIRST_RUN, false).apply();
    }

    public boolean isFirstRunApp(){
        return mSharedPreferences.getBoolean(FIRST_RUN, true);
    }

}
