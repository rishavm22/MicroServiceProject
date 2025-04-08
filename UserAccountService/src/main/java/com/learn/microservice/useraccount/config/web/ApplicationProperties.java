package com.learn.microservice.useraccount.config.web;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import static org.apache.logging.log4j.util.Strings.EMPTY;

@Configuration
@Data
public class ApplicationProperties {

    @Value("${app.jwt.key}")
    private String key = EMPTY;

    @Value("${app.jwt.refreshToken}")
    private String refreshToken;
}
