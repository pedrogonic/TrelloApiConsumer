package com.pedrogonic.trelloapiconsumer;

import com.pedrogonic.trelloapiconsumer.config.AppConfig;
import com.pedrogonic.trelloapiconsumer.service.TrelloService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.*;

@SpringBootApplication
@Log4j2
public class TrelloApiConsumerApplication {

    public static void main(String[] args) {

        SpringApplication.run(TrelloApiConsumerApplication.class, args);

        List<String> argsServices = new ArrayList<>();

        switch(args.length) {
            case 0:
                log.error("Usage: java com.pedrogonic.trelloapiconsumer.TrelloApiConsumerApplication serviceClass1,serviceClass2,..");
                return;
            default:
                argsServices.addAll(Arrays.asList(args[0].replaceAll(" ", "").split(",")));
        }

        argsServices.stream().forEach(
                serviceName -> {

                    log.info("Trying to run: " + serviceName);

                    try {
                        ClassLoader classLoader = TrelloApiConsumerApplication.class.getClassLoader();
                        Class c = classLoader.loadClass(serviceName);
                        TrelloService s = (TrelloService) c.newInstance();
                        // TODO: pass args
                        s.run(null);
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                });

    }

}
