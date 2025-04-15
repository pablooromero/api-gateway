package com.api_gateway.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouter(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route("sales-point-service", r -> r.path("/api/sales-point/**")
                        .uri("lb://sales-point-service") )
                .route("sales-point-service", r -> r.path("/api/costs/**")
                        .uri("lb://sales-point-service") )
                .route("accreditations-service", r -> r.path("/api/accreditations/**")
                        .uri("lb://accreditations-service") )
                .build();
    }
}
