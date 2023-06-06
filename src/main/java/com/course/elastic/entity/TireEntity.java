package com.course.elastic.entity;

import com.course.elastic.dto.TireDto;

import javax.persistence.*;

@Entity
@Table(name = "tire")
public class TireEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String manufacturer;

    private int size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carId")
    private CarEntity carEntity;

    public TireEntity() {
    }

    public TireEntity(String manufacturer, int size, CarEntity carEntity) {
        this.manufacturer = manufacturer;
        this.size = size;
        this.carEntity = carEntity;
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

    public CarEntity getCarEntity() {
        return carEntity;
    }

    public void setCarEntity(CarEntity carEntity) {
        this.carEntity = carEntity;
    }

    public static TireDto toDto(TireEntity entity) {
        return new TireDto(entity.getId(), entity.getManufacturer(), entity.getSize());
    }
}
