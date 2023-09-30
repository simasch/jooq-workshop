package ch.martinelli.edu.jooq.sakila;

import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SakilaJooqConfiguration {

    @Bean
    public DefaultConfigurationCustomizer configurationCustomizer() {
        return (DefaultConfiguration c) -> c.settings()
                .withExecuteWithOptimisticLocking(true);
    }
}
