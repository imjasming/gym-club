package com.gymclub.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ComponentScan(value = {"com.gymclub.sso.service", "com.gymclub.sso.controller", "com.gymclub.sso.component", "com.gymclub.sso.config", "com.gymclub.sso.handler", "com.gymclub.sso.jwt"})
@SpringBootApplication
public class SsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }

}

