package com.example.management;

import android.provider.BaseColumns;

public class Student {
    private int id;
    private String date;
    private String temperature;
    private String address;
    private String others;
    public Student(String date,String temperature,String address,String others)
    {
        this.date=date;
        this.temperature=temperature;
        this.address=address;
        this.others=others;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public static abstract class Student_information implements BaseColumns {
        public static final String TABLE_NAME="studnet_information";
        public static final String COLUMN_NAME_DATE="date";
        public static final String COLUMN_NAME_TEMPERATURE="temperature";
        public static final String COLUMN_NAME_ADDRESS="address";
        public static final String COLUMN_NAME_OTHERS="others";
    }

}

