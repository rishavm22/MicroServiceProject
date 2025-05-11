package com.apigateway.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@Slf4j
public class MyPreFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("MyPreFilter is called");
        var requestPath = exchange.getRequest().getPath().toString();
        log.info("Request Path is {}", requestPath);
        var headers = exchange.getRequest().getHeaders();

        Set<String> headerNames = headers.keySet();
        headerNames.forEach(headerName -> {
            var headerValue = headers.getFirst(headerName);
            log.info("Header Name is {} and Header Value is {}", headerName, headerValue);
        });
        return chain.filter(exchange);
    }
}
