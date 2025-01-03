package com.example.demospringgateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Pre Filter: Request path -> {}", request.getPath());
            log.info("Pre Filter: Request method -> {}", request.getMethod());

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        log.info("Post Filter: Response status -> {}", response.getStatusCode());
                    }));
        };
    }

    @Data
    public static class Config {
    }
}