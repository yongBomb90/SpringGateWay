package com.example.spg.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFIlter extends AbstractGatewayFilterFactory<LoggingFIlter.Config> {

    public LoggingFIlter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Custom Filter ...
        /*return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            log.info("Logging Filter BaseMessage : {} " , config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Logging Filter Start : request-id {} " , request.getId());
            }

            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                                if (config.isPostLogger()) {
                                    log.info("Logging Filter End : response-code {} " , response.getStatusCode());
                                }
                            }
                    ));

        };
        */

        GatewayFilter filter = new OrderedGatewayFilter(  (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            log.info("Logging Filter BaseMessage : {} " , config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Logging Filter Start : request-id {} " , request.getId());
            }

            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                                if (config.isPostLogger()) {
                                    log.info("Logging Filter End : response-code {} " , response.getStatusCode());
                                }
                            }
                    ));
        }, Ordered.LOWEST_PRECEDENCE );

        return filter;

    }

    @Data
    public static class Config {

        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;

        // TODO : 설정처리
    }
}
