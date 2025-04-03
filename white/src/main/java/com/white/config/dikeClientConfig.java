package com.white.config;

import com.netease.bahamut.dike.sdk.DefaultDikeClient;
import com.netease.bahamut.dike.sdk.DikeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class dikeClientConfig {

    @Bean
    public DikeClient dikeClient(@Value("${dike.url}") String url,
                                 @Value("${dike.app-id}") String appId,
                                 @Value("${dike.app-secret}") String appSecret){
        return DefaultDikeClient.builder(url, appId, appSecret)
                .setConnectionTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .setWriteTimeout(Duration.ofSeconds(10)).build();
    }
}
