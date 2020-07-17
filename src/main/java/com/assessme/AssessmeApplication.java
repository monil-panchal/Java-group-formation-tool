package com.assessme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/*

 * Spring boot starter class, serving as an entry point to this application.
 * Run the main method which will spawn an embedded server listening on http://localhost:8080/
 * Configurations are defined in application.properties file in resource directory.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.assessme.*")
@EnableAsync
public class AssessmeApplication {

    public static void
    main(String[] args) {
        SpringApplication.run(AssessmeApplication.class, args);

    }
}