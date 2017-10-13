package com.day.numen.urgent.helper;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.day.numen.R;
import com.day.numen.common.NumenOrmHelper;
import com.day.numen.common.OttoBus;
import com.day.numen.contacts.model.Contact;
import com.day.numen.urgent.event.MessageEvent;
import com.day.numen.urgent.model.UrgentContact;

import org.cn.orm.SimpleOrmHelper;

/**
 * Created by work on 17-9-29.
 */

public class UrgentHelper {

    public static void addUrgentContact(View view, Contact contact) {
        SimpleOrmHelper helper = NumenOrmHelper.getInstance();
        UrgentContact uc = (UrgentContact) helper.get(UrgentContact.class, contact.mobile);
        if (uc != null) {
            Snackbar.make(view, R.string.tip_urgent_contact_already_exist, Snackbar.LENGTH_LONG).show();
            return;
        }
        helper.save(new UrgentContact(contact.name, contact.mobile));
        OttoBus.getInstance().post(new MessageEvent("refresh", null));
    }

    public static void removeUrgentContact(UrgentContact contact) {
        SimpleOrmHelper helper = NumenOrmHelper.getInstance();
        helper.delete(contact);
        OttoBus.getInstance().post(new MessageEvent("refresh", null));
    }
}
