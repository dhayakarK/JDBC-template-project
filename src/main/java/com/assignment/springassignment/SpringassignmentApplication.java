package com.assignment.springassignment;

import com.assignment.springassignment.entities.FileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorage.class)
public class SpringassignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringassignmentApplication.class, args);
    }

}
