package com.api_gateway.api_gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Slf4j
public class ExtractJwtClaimsToHeadersGatewayFilter implements GatewayFilter, Ordered {

    public static final String HEADER_USER_EMAIL = "X-User-Email";
    public static final String HEADER_USER_ID = "X-User-Id";
    public static final String HEADER_USER_ROLE = "X-User-Role";

    public static final String CLAIM_USER_ID = "id";
    public static final String CLAIM_USER_ROLE = "role";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .filter(context -> context.getAuthentication() != null && context.getAuthentication().isAuthenticated())
                .map(SecurityContext::getAuthentication)
                .flatMap(authentication -> {
                    if (authentication.getPrincipal() instanceof Jwt) {
                        Jwt jwt = (Jwt) authentication.getPrincipal();
                        Map<String, Object> claims = jwt.getClaims();

                        String email = jwt.getSubject();
                        String userId = jwt.getClaimAsString(CLAIM_USER_ID);
                        String role = jwt.getClaimAsString(CLAIM_USER_ROLE);

                        log.debug("ExtractJwtClaimsFilter: Añadiendo headers: {}={}, {}={}, {}={}",
                                HEADER_USER_EMAIL, email, HEADER_USER_ID, userId, HEADER_USER_ROLE, role);

                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header(HEADER_USER_EMAIL, email != null ? email : "null")
                                .header(HEADER_USER_ID, userId != null ? userId : "null")
                                .header(HEADER_USER_ROLE, role != null ? role : "null")
                                .build();

                        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                        return chain.filter(mutatedExchange);
                    } else {
                        log.warn("ExtractJwtClaimsFilter: El principal de Authentication no es un objeto Jwt. Tipo: {}",
                                authentication.getPrincipal() != null ? authentication.getPrincipal().getClass().getName() : "null");
                        return chain.filter(exchange);
                    }
                })
                .switchIfEmpty(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
