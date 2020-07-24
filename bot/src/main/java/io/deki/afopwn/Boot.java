package io.deki.afopwn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Boot extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Boot.class, args);
    }

}
