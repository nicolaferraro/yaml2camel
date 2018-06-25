package me.nicolaferraro.yaml2camel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

@SpringBootApplication
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Configuration
    public static class RoutesConfig {

        @Bean
        public YamlRouteBuilder yamlRouteBuilder(@Value("${camel.routes}") String camelRoutes) throws IOException {
            LOG.info("Routes loaded: {}", camelRoutes);

            YamlRouteBuilder builder;
            try (Reader reader = new StringReader(camelRoutes)) {
                builder = new YamlRouteBuilder(reader);
            }

            return builder;
        }

    }

}
