package com.example.demospringgateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            log.info("Global Filter baseMessage: {}", config.getBaseMessage());
            log.info("Global Filter Start: request id -> {}", exchange.getRequest().getId());

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        log.info("Global Filter End: response code -> {}",
                                exchange.getResponse().getStatusCode());
                    }));
        });
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}