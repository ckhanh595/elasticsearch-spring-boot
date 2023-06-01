package com.course.elastic.repository;

import com.course.elastic.entity.Car;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarElasticRepository extends ElasticsearchRepository<Car, String> {

    List<Car> findByBrandAndColor(String brand, String color);

    List<Car> findByFirstReleaseDateAfter(LocalDate firstReleaseDate);
}
