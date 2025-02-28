package com.hmall.gateway.filters;

import com.hmall.gateway.config.AuthProperties;
import com.hmall.gateway.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @Author: tmind
 * @Date: 2025/2/27 19:55
 * @Description:
 */

@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final AuthProperties authProperties;

    private final JwtTool jwtTool;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("登录全局过滤器");
        ServerHttpRequest request = exchange.getRequest();
        if (isExclude(request.getPath().toString())) {
            return chain.filter(exchange);
        }
        String token = null;
        String authorization = request.getHeaders().getFirst("Authorization");
        if (authorization != null) {
            token = authorization;
        }
        Long userId = null;
        try {
            userId = jwtTool.parseToken(token);
        } catch (Exception e) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        System.out.println("userId = " + userId);
        String userIdString = userId.toString();
        return chain.filter(exchange.mutate().request(builder -> builder.header("user-info", userIdString)).build());
    }


    @Override
    public int getOrder() {
        return -1;
    }

    public boolean isExclude(String path) {
        for (String excludePath : authProperties.getExcludePaths()) {
            if (antPathMatcher.match(excludePath, path)) {
                return true;
            }
        }
        return false;
    }
}
