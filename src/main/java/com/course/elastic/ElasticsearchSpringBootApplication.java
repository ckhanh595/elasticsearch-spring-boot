package com.course.elastic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ElasticsearchSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchSpringBootApplication.class, args);
	}

}
