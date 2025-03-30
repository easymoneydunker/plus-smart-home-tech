package ru.yandex.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import ru.yandex.practicum.snapshot.AggregatorStarter;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AggregatorServer {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AggregatorServer.class, args);

        AggregatorStarter aggregator = context.getBean(AggregatorStarter.class);
        aggregator.start();
    }
}