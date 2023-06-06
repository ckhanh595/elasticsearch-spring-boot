package com.course.elastic.dto;

import java.time.LocalDate;
import java.util.List;

public class CarDto {
    private Long id;
    private boolean available;
    private String brand;
    private String color;
    private String type;
    private LocalDate firstReleaseDate;
    private int price;
    private List<TireDto> tires;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(LocalDate firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<TireDto> getTires() {
        return tires;
    }

    public void setTires(List<TireDto> tires) {
        this.tires = tires;
    }
}
