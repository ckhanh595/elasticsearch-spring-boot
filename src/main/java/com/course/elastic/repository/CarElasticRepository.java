package com.course.elastic.repository;

import com.course.elastic.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarElasticRepository extends ElasticsearchRepository<Car, String> {

    //    List<Car> findByBrandAndColor(String brand, String color);
    Page<Car> findByBrandAndColor(String brand, String color, Pageable pageable);

    List<Car> findByFirstReleaseDateAfter(LocalDate firstReleaseDate);


    @Query("{" +
            "  \"multi_match\": {\n" +
            " \"query\": \"?0\" ,\n" +
            " \"fields\": [  \"brand\", \"color\"] \n" +
            "}}")
    Page<Car> findByKeyword(String keyword, Pageable pageable);
}
