package com.example.spg.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFIlter extends AbstractGatewayFilterFactory<CustomFIlter.Config> {

    public CustomFIlter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Custom Filter ...
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            log.info("Custom PRE filter : req is " + request.getId());

            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                                log.info("Custom POST filter : res is {}", response.getStatusCode());
                            }
                    ));

        };
    }

    public static class Config {
        // TODO : 설정처리
    }
}
