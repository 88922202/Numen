package com.day.numen.service.task;

/**
 * Created by work on 17-9-28.
 */

public interface ReportTask {
    void sendMessage(String consumer, String payload);
}
