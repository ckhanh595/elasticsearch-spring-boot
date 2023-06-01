package com.course.elastic.api.server;

import com.course.elastic.entity.Car;
import com.course.elastic.service.CarElasticService;
import com.course.elastic.service.RandomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequestMapping(value = "/api/v1/car")
@RestController
public class CarController {
    private static final Logger LOG = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private RandomService randomService;

    @Autowired
    private CarElasticService carElasticService;

    @GetMapping(value = "/random")
    public Car random() {
        return randomService.generateCar();
    }

    @PostMapping(value = "/echo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String echo(@RequestBody Car car) {
        LOG.info("Car is {}", car);
        return car.toString();
    }

    @GetMapping(value = "/random-cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Car> randomCars() {
        var result = new ArrayList<Car>();
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(1, 10); i++) {
            result.add(randomService.generateCar());
        }
        return result;
    }

    @GetMapping(value = "/count")
    public String count() {
        return "There are " + carElasticService.countCars() + " cars";
    }

    @PostMapping(value = "")
    public String create(@RequestBody Car car) {
        var id = carElasticService.create(car).getId();
        return "Saved with ID = " + id;
    }

    @GetMapping(value = "/{id}")
    public Car findById(@PathVariable String id) {
        return carElasticService.findById(id);
    }

    @PutMapping(value = "/{id}")
    public Car update(@PathVariable String id, @RequestBody Car car) {
        car.setId(id);
        return carElasticService.update(car);
    }

    @GetMapping(value = "/find")
    public List<Car> search(@RequestParam String brand, @RequestParam String color) {
        return carElasticService.search(brand, color);
    }

    @GetMapping(value = "/date")
    public List<Car> findCarsReleasedAfter(@RequestParam(name = "first_release_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate firstReleaseDate) {
        return carElasticService.findByFirstReleaseDateAfter(firstReleaseDate);
    }
}
