package org.example.model;

import java.time.LocalDateTime;

public class LogItem {
    private final String time;
    private final String action;

    public LogItem(String action) {
        this.time = LocalDateTime.now().toString();
        this.action = action;
    }

    public String getTime() { return time; }
    public String getAction() { return action; }
}
