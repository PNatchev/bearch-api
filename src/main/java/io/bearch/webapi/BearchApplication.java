package io.bearch.webapi;

import io.bearch.webapi.config.security.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(SecurityProperties.class)
@SpringBootApplication
public class BearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BearchApplication.class, args);
    }

}
