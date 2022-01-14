package com.example.spg.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Date;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    Environment environment;

    public static class Config {
    }

    public AuthorizationHeaderFilter(Environment environment) {
        super(Config.class);
        this.environment = environment;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if ( !request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "no Authoriztion Header", HttpStatus.UNAUTHORIZED);
            }

            String authoriztionHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authoriztionHeader.replace("Bearer", "");

            if ( !isJwtValid(jwt)) {
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }





            return chain.filter(exchange);

        };
    }
    // Mono , Flux -> spring WebFlux
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {

        ServerHttpResponse response =  exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(err);


        return response.setComplete();

    }

    private boolean isJwtValid(String jwt) {

        boolean returnValue = true;

        String subject = null;

        try {

            subject = Jwts.parser().setSigningKey(environment.getProperty("token.secret"))
                    .parseClaimsJws(jwt).getBody().getSubject();
        } catch (Exception e ) {
            returnValue = false;
        }
        if ( subject == null || subject.isEmpty()) {
            returnValue = false;
        }

        return returnValue;

    }




}
