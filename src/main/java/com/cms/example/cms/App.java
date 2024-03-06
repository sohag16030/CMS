package com.cms.example.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        logger.info("========================= @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ PROJECT STARTED  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ =====================");
    }

}
