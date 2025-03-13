package com.hmall.order.listener;

import com.hmall.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: tmind
 * @Date: 2025/3/11 15:34
 * @Description:
 */
@Slf4j
@Component
public class SpringRabbitListener {
    @Autowired
    private IOrderService orderService;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "order.queue", durable = "true"),
            exchange = @Exchange(value = "pay.order.exchange", type = ExchangeTypes.TOPIC),
            key = {"success"}))
    public void orderPaySuccessMq(String msg) {
        log.info("orderPaySuccessMq监听到pay success消息:{}", msg);
        orderService.markOrderPaySuccess(Long.valueOf(msg));
    }
}
