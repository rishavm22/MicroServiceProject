package com.apigateway.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class GlobalFilterConfiguration {

    @Bean
    public GlobalFilter secondPreFilter() {
        return (exchange, chain) -> {
            log.info("Second Pre Filter is called");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Second Post Filter is called");
            }));
        };
    }
}
