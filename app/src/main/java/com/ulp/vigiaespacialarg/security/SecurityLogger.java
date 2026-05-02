package com.ulp.vigiaespacialarg.security;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SecurityLogger {
    private static final List<String> logs = new ArrayList<>();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    public static void addLog(String tag, String message) {
        String timestamp = dateFormat.format(new Date());
        logs.add("[" + timestamp + "] " + tag + ": " + message);
    }

    public static List<String> getLogs() {
        return new ArrayList<>(logs);
    }
}