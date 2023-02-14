package com.anika.core.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:core.properties")
public class PostgresqlConfiguration {
}
