package com.gymclub.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//@ComponentScan(basePackages = "com.gymclub.core")
//@EnableJpaRepositories("com.gymclub.core.repository")
@EntityScan("com.gymclub.core.domain")
@SpringBootApplication
public class GymClubSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymClubSystemApplication.class, args);
    }

}
