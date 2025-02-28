package com.hmall.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: tmind
 * @Date: 2025/2/27 19:56
 * @Description:
 */
@Component
public class PrintGatewayFilterFactory extends AbstractGatewayFilterFactory {


    @Override
    public GatewayFilter apply(Object config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            System.out.println("------PrintFilterGatewayFilterFactory------");
            return chain.filter(exchange);
        }, 1);
    }
}
