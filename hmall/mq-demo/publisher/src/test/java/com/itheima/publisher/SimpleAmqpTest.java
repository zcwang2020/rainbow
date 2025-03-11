package com.itheima.publisher;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonbTester;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SimpleAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void testSimpleQueue() {
        // 队列名称
        String queueName = "simple.queue";
        // 消息
        String message = "hello,AMQP";
        for (int i = 1; i <= 50; i++) {
            rabbitTemplate.convertAndSend(queueName, "", message + i);
        }
    }

    @Test
    void testFanoutQueue() {
        // 交换机名称
        String exchange = "hmall.fanout";
        // 消息
        String message = "hello,fanout everyone";
        rabbitTemplate.convertAndSend(exchange, "", message);
    }

    @Test
    void testDirectQueue() {
        // 交换机名称
        String exchange = "hmall.direct";
        // 消息
        String message = "hello,direct everyone";
        rabbitTemplate.convertAndSend(exchange, "red", message + "_red");
        rabbitTemplate.convertAndSend(exchange, "pink", message + "_pink");
        rabbitTemplate.convertAndSend(exchange, "yellow", message + "_yellow");
        rabbitTemplate.convertAndSend(exchange, "black", message + "_black");
    }

    @Test
    void testTopicQueue() {
        // 交换机名称
        String exchange = "hmall.topic";
        // 消息
        String message = "hello,topic everyone";
        rabbitTemplate.convertAndSend(exchange, "china.news", message + "_china.news");
        rabbitTemplate.convertAndSend(exchange, "china. no.1", message + "_china.#");
        rabbitTemplate.convertAndSend(exchange, "fack .news", message + "_#.news");
    }

    @Test
    void testMessageConverter() {
        // 队列名称
        String queueName = "object.queue";
        // 消息
        Map<String, Object> messaege = new HashMap<>();
        messaege.put("name", "zhangsan");
        messaege.put("age", 18);
        rabbitTemplate.convertAndSend(queueName, messaege);
    }
}