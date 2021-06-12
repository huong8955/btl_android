package com.example.myapplication;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String title;
    private String date;
    private String desc;

    public Item(String title, String date, String desc) {
        this.title = title;
        this.date = date;
        this.desc = desc;
    }

    public Item(int id, String title, String date, String desc) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
