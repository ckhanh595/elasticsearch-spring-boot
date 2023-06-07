package com.course.elastic.controller;

import com.course.elastic.dto.response.ErrorResponse;
import com.course.elastic.entity.Car;
import com.course.elastic.exception.IllegalApiParamException;
import com.course.elastic.service.CarElasticService;
import com.course.elastic.service.RandomService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public ResponseEntity<Object> search(@RequestParam String brand, @RequestParam String color, @RequestParam int page, @RequestParam int pageSize) {
        var pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC,"price"));
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.SERVER, "CK Spring");
        headers.add("X-Custom-Header", "Custom Response Header");

//        if (StringUtils.isNumeric(color)) {
//            var errorResponse = new ErrorResponse("Invalid color : " + color, LocalDateTime.now());
//            return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
//        }

        if (StringUtils.isNumeric(color)) {
            throw new IllegalStateException("Invalid color : " + color);
        }

        if (StringUtils.isNumeric(brand)) {
            throw new IllegalStateException("Invalid brand : " + color);
        }

        var cars = carElasticService.search(brand, color, pageable);
        return ResponseEntity.ok().headers(headers).body(cars);
    }

    @GetMapping(value = "/date")
    public List<Car> findCarsReleasedAfter(@RequestParam(name = "first_release_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate firstReleaseDate) {
        return carElasticService.findByFirstReleaseDateAfter(firstReleaseDate);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    private ResponseEntity<ErrorResponse> handleInvalidColorEx(IllegalStateException e) {
        var message = "Exception " + e.getMessage();
        LOG.warn(message);
        var errorResponse = new ErrorResponse(message, LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalApiParamException.class)
    private ResponseEntity<ErrorResponse> handleInvalidApiParamException(IllegalApiParamException e) {
        var message = "Exception API param " + e.getMessage();
        LOG.warn(message);
        var errorResponse = new ErrorResponse(message, LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
