package com.pedrogonic.trelloapiconsumer;

import com.pedrogonic.trelloapiconsumer.config.AppConfig;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Slf4j
public class TrelloApiConsumerApplication {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TrelloApiConsumerApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(TrelloApiConsumerApplication.class, args);

        log.info(AppConfig.API_KEY);
        log.info(AppConfig.API_TOKEN);
    }

}
