package com.dipartimento.demowebapplications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class DemoWebApplicationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoWebApplicationsApplication.class, args);
    }

}
