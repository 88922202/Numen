package com.day.numen.urgent.model;

import org.cn.orm.annotation.Column;
import org.cn.orm.annotation.Entity;
import org.cn.orm.annotation.Id;

import java.util.UUID;

/**
 * Created by work on 17-9-28.
 */

@Entity(name = "urgent_contact")
public class UrgentContact {
    @Id
    @Column(name = "id", length = 50)
    public String id = UUID.randomUUID().toString();
    @Column(name = "_name", length = 50)
    public String name;
    @Column(name = "_mobile", length = 50)
    public String mobile;
    @Column(name = "_timestamp")
    public long timestamp = System.currentTimeMillis();

    public UrgentContact() {
    }

    public UrgentContact(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
        this.id = mobile;
    }

    @Override
    public String toString() {
        return "UrgentContact{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
