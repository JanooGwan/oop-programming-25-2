package org.example.repository;

import org.example.model.LogItem;

import java.util.ArrayList;
import java.util.List;

public class LogRepository {
    private static LogRepository instance;
    private final List<LogItem> logs = new ArrayList<>();

    public static LogRepository getInstance() {
        if (instance == null) instance = new LogRepository();
        return instance;
    }

    public void add(String action) {
        logs.add(new LogItem(action));
    }

    public List<LogItem> findAll() {
        return logs;
    }
}
