package com.itheima.consumer.config;

/**
 * @Author: tmind
 * @Date: 2025/3/11 17:41
 * @Description:
 */

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NormalConfiguration {

    // 测试死信交换机
    // 声明FanoutExchange交换机
    @Bean
    public DirectExchange normalExchange(){
        // return ExchangeBuilder.fanoutExchange("normal.fanout").durable(true).build();
        return new DirectExchange("normal.direct");
    }

    // 声明第1个队列
    @Bean
    public Queue normalQueue(){
        return QueueBuilder.durable("normal.queue").build();
        // return new Queue("normal.queue");
    }

    //绑定normal队列和normal交换机
    @Bean
    public Binding normalBindingQueue(Queue normalQueue, DirectExchange normalExchange){
        return BindingBuilder.bind(normalQueue).to(normalExchange).with("dlx");
    }
}

