package com.course.elastic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tire {
    private String manufacturer;

//    @JsonProperty(value = "diameter")
    private int size;

    public Tire() {
    }

    public Tire(String manufacturer, int size) {
        this.manufacturer = manufacturer;
        this.size = size;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getSize() {
        return size;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Tire [manufacturer=" + manufacturer + ", size=" + size + "]";
    }
}
