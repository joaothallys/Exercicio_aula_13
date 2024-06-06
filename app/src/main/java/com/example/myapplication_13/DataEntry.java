package com.example.myapplication_13;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataEntry {
    private String data;
    private String dateTime;

    public DataEntry(String data) {
        this.data = data;
        this.dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    public String getData() {
        return data;
    }

    public String getDateTime() {
        return dateTime;
    }
}
