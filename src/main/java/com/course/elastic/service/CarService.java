package com.course.elastic.service;

import com.course.elastic.dto.CarDto;
import com.course.elastic.entity.Car;
import com.course.elastic.entity.CarEntity;
import com.course.elastic.entity.Tire;
import com.course.elastic.entity.TireEntity;
import com.course.elastic.repository.CarElasticRepository;
import com.course.elastic.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class CarService {
    @Autowired
    private CarElasticRepository carElasticRepository;

    @Autowired
    private CarRepository carRepository;

    public long countCars() {
        return carElasticRepository.count();
    }

    public Car create(CarDto carDto) {
        // MySQL
        var car = new CarEntity();
        car.setColor(carDto.getColor());
        car.setBrand(carDto.getBrand());
        car.setType(carDto.getType());
        car.setAvailable(carDto.isAvailable());
        car.setPrice(carDto.getPrice());
        car.setFirstReleaseDate(carDto.getFirstReleaseDate());
        var tireList = carDto.getTires().stream()
                .map(tire -> new TireEntity(tire.getManufacturer(), tire.getSize(), car)).collect(toList());
        car.setTires(tireList);
        var savedCar = carRepository.save(car);

        // Elastic
        var carElastic = new Car();
        carElastic.setColor(carDto.getColor());
        carElastic.setBrand(carDto.getBrand());
        carElastic.setType(carDto.getType());
        carElastic.setAvailable(carDto.isAvailable());
        carElastic.setPrice(carDto.getPrice());
        carElastic.setFirstReleaseDate(carDto.getFirstReleaseDate());
        var tireElasticList = carDto.getTires().stream()
                .map(tire -> new Tire(tire.getManufacturer(), tire.getSize())).collect(toList());
        carElastic.setTires(tireElasticList);
        carElastic.setId(savedCar.getId().toString());
        return carElasticRepository.save(carElastic);
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

    public List<Car> searchByKeyword(String keyword, Pageable pageable) {
        return carElasticRepository.findByKeyword(keyword, pageable).getContent();
    }

    public List<Car> searchByKeywordOptimized(String keyword, Pageable pageable) {
        return carElasticRepository.findByKeywordOptimized(keyword, pageable).getContent();
    }

    public List<Car> searchByKeywordFuzzy(String keyword, Pageable pageable) {
        return carElasticRepository.findByKeywordFuzzy(keyword, pageable).getContent();
    }
}
