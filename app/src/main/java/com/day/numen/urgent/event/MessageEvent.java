package com.day.numen.urgent.event;

/**
 * Created by work on 17-9-29.
 */

public class MessageEvent {
    public String type;
    public String payload;

    public MessageEvent(String type, String payload) {
        this.type = type;
        this.payload = payload;
    }
}
