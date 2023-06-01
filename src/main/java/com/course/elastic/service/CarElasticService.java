package com.course.elastic.service;

import com.course.elastic.entity.Car;
import com.course.elastic.repository.CarElasticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarElasticService {
    @Autowired
    private CarElasticRepository carElasticRepository;

    public long countCars() {
        return carElasticRepository.count();
    }

    public Car create(Car car) {
        return carElasticRepository.save(car);
    }

    public Car findById(String id) {
        return carElasticRepository.findById(id).orElse(null);
    }

    public Car update(Car updatedCar) {
        return carElasticRepository.save(updatedCar);
    }

    public List<Car> search(String brand, String color, Pageable pageable) {
        return carElasticRepository.findByBrandAndColor(brand, color, pageable).getContent();
    }

    public List<Car> findByFirstReleaseDateAfter(LocalDate firstReleaseDate) {
        return carElasticRepository.findByFirstReleaseDateAfter(firstReleaseDate);
    }
}
