package com.itheima.publisher;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SimpleAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    private void testSimpleQueue() {
        // 队列名称
        String queueName = "simple.exchange";
        // 消息
        String message = "hello,rabbitmq";
        rabbitTemplate.convertAndSend(queueName, message);
    }
}