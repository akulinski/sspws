package com.akulinski.sspws;

import com.akulinski.sspws.config.UserMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan({"com.akulinski.sspws.core", "com.akulinski.sspws.config"})
public class SspwsApplication {

    @Autowired
    private UserMock userMock;

    public static void main(String[] args) {
        SpringApplication.run(SspwsApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void mockData() {
        userMock.mockData();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

