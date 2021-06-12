package com.example.myapplication;

import java.io.Serializable;

public class WeatherItem implements Serializable {

    private long date;
    private int image;
    private String min_temp,max_temp;

    public WeatherItem(long date, int image, String min_temp, String max_temp) {
        this.date = date;
        this.image = image;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }
}
