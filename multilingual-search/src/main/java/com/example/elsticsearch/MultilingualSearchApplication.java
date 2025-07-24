package com.example.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Multilingual Search Spring Boot application.
 * <p>
 * This application provides REST APIs for inserting and searching multilingual documents in Elasticsearch.
 */
@SpringBootApplication
public class MultilingualSearchApplication {
    /**
     * Application entry point.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MultilingualSearchApplication.class, args);
    }
} 