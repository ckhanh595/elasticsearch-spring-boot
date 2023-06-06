package com.course.elastic.job;

import com.course.elastic.entity.Car;
import com.course.elastic.entity.CarEntity;
import com.course.elastic.entity.Tire;
import com.course.elastic.repository.CarElasticRepository;
import com.course.elastic.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

@Service
public class ElasticSynchronizer {
    private static final Logger LOG = LoggerFactory.getLogger(ElasticSynchronizer.class);

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarElasticRepository carElasticRepository;

    @Scheduled(cron = "0 */3 * * * *")
    @Transactional
    public void sync() {
        LOG.info("Start Syncing - {}", LocalDateTime.now());
        this.syncData();
        LOG.info("End Syncing - {}", LocalDateTime.now());
    }

    private void syncData() {
        var carList = carRepository.findAll();

        var carElasticList = new ArrayList<Car>();
        carList.forEach(car -> {
            var tireEntityList = car.getTires();
            var tireElasticList = tireEntityList.stream()
                    .map(tire -> new Tire(tire.getManufacturer(), tire.getSize())).collect(toList());
            var carElastic = convertMysqlEntityToElastic(car);
            carElastic.setTires(tireElasticList);
            carElasticList.add(carElastic);
        });
        carElasticRepository.saveAll(carElasticList);
    }

    private Car convertMysqlEntityToElastic(CarEntity carEntity) {
        return new Car(carEntity.getId().toString(), carEntity.isAvailable(), carEntity.getBrand(),
                carEntity.getColor(), carEntity.getType(), carEntity.getFirstReleaseDate(), carEntity.getPrice());
    }

}
