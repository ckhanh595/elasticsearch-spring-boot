package com.course.elastic.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@RequestMapping(value = "/api")
public class DefaultRestApi {

    private Logger LOG = LoggerFactory.getLogger(DefaultRestApi.class);

    @GetMapping(value = "/welcome")
    public String welcome() {
        LOG.info(StringUtils.join("Hello", " this is", " Spring boot", " REST API"));
        return "Welcome to spring boot";
    }

    @GetMapping(value = "/time")
    public String time() {
        return LocalTime.now().toString();
    }

    @GetMapping(value = "/header-one")
    public String headerByAnnotation(@RequestHeader(name = "User-agent") String headerUserAgent,
                                     @RequestHeader(name = "Practical-java", required = false) String headerPracticalJava) {
        return "User-agent : " + headerUserAgent + ", Practical-java : " + headerPracticalJava;
    }

    @GetMapping(value = "/header-two")
    public String headerByRequest(ServerHttpRequest request) {
        return "User-agent : " + request.getHeaders().getValuesAsList("User-agent") + ", Practical-java : "
                + request.getHeaders().getValuesAsList("Practical-java");
    }
}
