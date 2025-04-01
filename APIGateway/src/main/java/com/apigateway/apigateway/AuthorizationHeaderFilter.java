package com.apigateway.apigateway;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Autowired
    private Environment env;

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No Authorization header found", HttpStatus.UNAUTHORIZED);
            }

            var authorizationToken = request.getHeaders().remove(HttpHeaders.AUTHORIZATION).get(0);
            var token = authorizationToken.substring(7);

            if (!token.startsWith("Bearer ")) {
                return onError(exchange, "Invalid Authorization header", HttpStatus.UNAUTHORIZED);
            }

            if (!isValidToken(token)) {
                return onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    private boolean isValidToken(String token) {
        var isValid = true;

        var tokenSecret = env.getProperty("jwt.secret");
        String subject = null;
        if (tokenSecret == null) {
            return false;
        }

        try {
            // Create the signing key
            byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
            SecretKey signInKey = Keys.hmacShaKeyFor(secretKeyBytes);

            Jwts.parser().decryptWith(signInKey).build().parse(token);
            Claims claims = Jwts.parser()
                    .verifyWith(signInKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

//            JwtParser parser = Jwts.parser()
//                    .verifyWith(signInKey)
//                    .build();

            // Parse and validate the token
            subject = claims.getSubject();

            return subject != null && !subject.isEmpty();
        } catch (JwtException | IllegalArgumentException e) {
            // Catch parsing and validation errors
            return false;
        }
    }

}
