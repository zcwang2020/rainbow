package com.itheima.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: tmind
 * @Date: 2025/3/11 15:34
 * @Description:
 */
@Slf4j
@Component
public class SpringRabbitListener {

    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue1(String msg) throws InterruptedException {
        log.info("消费者---1---接收到消息:{}", msg);
        Thread.sleep(25);
    }

    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue2(String msg) throws InterruptedException {
        log.error("消费者--2---接收到消息:{}", msg);
        Thread.sleep(200);
    }

    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String msg) throws InterruptedException {
        log.error("消费者--1---监听到fanout.queue1消息:{}", msg);
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String msg) throws InterruptedException {
        log.error("消费者--2---监听到fanout.queue2消息:{}", msg);
    }

    /*@RabbitListener(queues = "direct.queue1")
    public void listenDirectQueue1(String msg) {
        log.error("消费者--1---监听到direct.queue1消息:{}", msg);
    }*/

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(value = "direct.queue1", durable = "true"),
                    exchange = @Exchange(value = "hmall.direct"),
                    key = {"red", "pink"}),
            @QueueBinding(value = @Queue(value = "direct.queue2", durable = "true"),
                    exchange = @Exchange(value = "hmall.direct"),
                    key = {"red", "yellow"})
    })
    public void listenDirectQueue1(String msg) {
        log.error("listenDirectQueue1--1---监听到direct.queue1消息:{}", msg);
    }

    /*@RabbitListener(queues = "direct.queue2")
    public void listenDirectQueue2(String msg) {
        log.error("消费者--2---监听到direct.queue2消息:{}", msg);
    }*/

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "direct.queue3", durable = "true"),
            exchange = @Exchange(value = "hmall.direct"),
            key = {"red", "black"}))
    public void listenDirectQueue2(String msg) {
        log.error("listenDirectQueue2--2---监听到direct.queue3消息:{}", msg);
    }
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "object.queue", durable = "true"),
            exchange = @Exchange(value = "hmall.object"),
            key = {"red", "black"}))
    public void listenObjectQueue(Map msg) {
        log.error("listenObjectQueue--1---监听到object.queue消息:{}", msg);
    }


    @RabbitListener(queues = "topic.queue1")
    public void listenTopicQueue1(String msg) {
        log.error("消费者--1---监听到topic.queue1消息:{}", msg);
    }

    @RabbitListener(queues = "topic.queue2")
    public void listenTopicQueue2(String msg) {
        log.error("消费者--2---监听到topic.queue2消息:{}", msg);
    }
}
