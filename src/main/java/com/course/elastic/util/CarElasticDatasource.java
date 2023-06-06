package com.course.elastic.util;

import com.course.elastic.entity.Car;
import com.course.elastic.entity.CarEntity;
import com.course.elastic.repository.CarElasticRepository;
import com.course.elastic.repository.CarRepository;
import com.course.elastic.repository.TireRepository;
import com.course.elastic.service.RandomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Component
public class CarElasticDatasource {
    private static final Logger LOG = LoggerFactory.getLogger(CarElasticDatasource.class);

    @Autowired
    private CarElasticRepository carElasticRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TireRepository tireRepository;

    @Autowired
    @Qualifier("randomCarService")
    private RandomService randomService;

    @Autowired
    @Qualifier("webClientElasticsearch")
    private WebClient webClient;

//    @EventListener(ApplicationReadyEvent.class)
//    public void populateData() {
//        var response = webClient.delete().uri("/elastic-spring-boot").retrieve().bodyToMono(String.class).block();
//        LOG.info("End delete with response {}", response);
//
//        var cars = new ArrayList<Car>();
//        for (int i = 0; i < 10_000; i++) {
//            cars.add(randomService.generateCar());
//        }
//
//        carElasticRepository.saveAll(cars);
//        LOG.info("Saved {} cars", carElasticRepository.count());
//    }

//    @EventListener(ApplicationReadyEvent.class)
    public void populateDataMySql() {
//        var response = webClient.delete().uri("/elastic-spring-boot").retrieve().bodyToMono(String.class).block();
//        LOG.info("End delete with response {}", response);
//        tireRepository.deleteAll();
//        carRepository.deleteAll();
        LOG.info("Deleted all records");

        var cars = new ArrayList<CarEntity>();
        for (int i = 0; i < 10_000; i++) {
            var car = randomService.generateCarMySql();
            car.setId((long) i);
            cars.add(car);
        }

        carRepository.saveAll(cars);
        LOG.info("Saved {} cars", carElasticRepository.count());
    }
}
