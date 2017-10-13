package com.day.numen.common;

import com.squareup.otto.Bus;

/**
 * Created by work on 17-9-29.
 */

public class OttoBus {
    private static final Bus bus = new Bus();

    public static Bus getInstance() {
        return bus;
    }
}
