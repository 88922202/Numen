package com.day.numen.settings;

import org.cn.orm.annotation.Column;
import org.cn.orm.annotation.Entity;
import org.cn.orm.annotation.Id;

/**
 * Created by wangzhe on 29/9/2017.
 */

@Entity(name = "settings")
public class Settings {

    public static final String ID_PASSWORD = "password";
    public static final String ID_TASK_TYPE = "taskType";

    @Id
    @Column(name = "_key", length = 50)
    public String key = ID_PASSWORD;
    @Column(name = "_value", length = 50)
    public String value;

    public Settings() {
    }

    public Settings(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
