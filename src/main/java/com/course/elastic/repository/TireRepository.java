package com.course.elastic.repository;

import com.course.elastic.entity.Tire;
import com.course.elastic.entity.TireEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TireRepository extends JpaRepository<TireEntity, Long> {

}
