package com.api_gateway.api_gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AdminFilter adminFilter;

    @Bean
    public RouteLocator customRouter(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route("auth", r -> r.path("/api/auth/**")
                        .uri("lb://user-service") )
                .route("user-service", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://user-service") )
                .route("user-service-admin", r -> r.path("/api/admins/**")
                        .filters(f -> f.filters(adminFilter, jwtAuthenticationFilter))
                        .uri("lb://user-service") )
                .route("sales-point-admin", r -> r.path("/api/sales-point/admin/**")
                        .filters(f -> f.filters(adminFilter, jwtAuthenticationFilter))
                        .uri("lb://sales-point-service") )
                .route("sales-point-users", r -> r.path("/api/sales-point/**")
                        .filters(f -> f.filters(jwtAuthenticationFilter))
                        .uri("lb://sales-point-service") )
                .route("costs-admin", r -> r.path("/api/costs/admin/**")
                        .filters(f -> f.filters(adminFilter, jwtAuthenticationFilter))
                        .uri("lb://sales-point-service") )
                .route("costs-users", r -> r.path("/api/costs/**")
                        .filters(f -> f.filters(jwtAuthenticationFilter))
                        .uri("lb://sales-point-service") )
                .route("accreditations-admin", r -> r.path("/api/accreditations/admin/**")
                        .filters(f -> f.filters(adminFilter, jwtAuthenticationFilter))
                        .uri("lb://accreditations-service") )
                .route("accreditations-users", r -> r.path("/api/accreditations/**")
                        .filters(f -> f.filters(    jwtAuthenticationFilter))
                        .uri("lb://accreditations-service") )
                .route("oauth2-login", r -> r.path("/oauth2/**")
                        .uri("lb://user-service"))

                .route("oauth2-callback", r -> r.path("/login/oauth2/code/**")
                        .uri("lb://user-service"))
                .build();
    }
}
