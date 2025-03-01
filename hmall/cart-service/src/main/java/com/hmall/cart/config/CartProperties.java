package com.hmall.cart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: tmind
 * @Date: 2025/2/28 23:06
 * @Description:
 */

@Data
@Component
@ConfigurationProperties(prefix = "hmall.cart")
public class CartProperties {

    private Integer maxNum = 10;
}
