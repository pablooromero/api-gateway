package com.bootcamp.api_gateway.config;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Date;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    @Autowired
    private JwtUtils jwtUtil;


    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtil = jwtUtils;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (!exchange.getRequest().getPath().toString().startsWith("/api/auth")) {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                Date string = jwtUtil.parseClaims(token).getExpiration();
                try {
                    if (!jwtUtil.isTokenExpired(token)) {
                        Claims claims = jwtUtil.parseClaims(token);
                        exchange.getRequest().mutate().header("email", claims.getSubject()).build();
                        exchange.getRequest().mutate().header("id", jwtUtil.extractId(token).toString()).build();
                        exchange.getRequest().mutate().header("role", jwtUtil.extractRole(token)).build();
                    } else {
                        return onError(exchange, "Invalid JWT Token", HttpStatus.UNAUTHORIZED);
                    }
                } catch (Exception e) {
                    return onError(exchange, "JWT Token validation failed", HttpStatus.UNAUTHORIZED);
                }
            } else {
                return onError(exchange, "Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED);
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        return exchange.getResponse().setComplete();
    }
}