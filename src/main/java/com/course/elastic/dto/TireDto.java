package com.course.elastic.dto;

public class TireDto {
    private Long id;
    private String manufacturer;
    private int size;

    public TireDto() {
    }

    public TireDto(Long id, String manufacturer, int size) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
