package com.hmall.service.impl;

import com.hmall.domain.dto.OrderDetailDTO;
import com.hmall.utils.JwtTool;
import com.netease.bahamut.dike.sdk.DefaultDikeClient;
import com.netease.bahamut.dike.sdk.DikeClient;
import com.netease.bahamut.purchase.sdk.punchout.request.QueryCountryRequest;
import com.netease.bahamut.purchase.sdk.punchout.response.QueryCountryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

@SpringBootTest
class ItemServiceImplTest {

    @Autowired
    private JwtTool jwtTool;

    @Test
    void testJwt() {
        String token = jwtTool.createToken(1L, Duration.ofMinutes(30));

        System.out.println("token = " + token);
    }

    @Test
    void testGetOrderDetail() {
        DikeClient client = DefaultDikeClient.builder("https://test-dep176-open-dike.netease.com",
                        "your_appId", "your_appSecret")
                .setConnectionTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .setWriteTimeout(Duration.ofSeconds(10)).build();
        final QueryCountryRequest queryCountryRequest = new QueryCountryRequest();
        queryCountryRequest.setKeyword("B");
        QueryCountryResponse exec = client.exec(queryCountryRequest);
        System.out.println(exec);
    }

}