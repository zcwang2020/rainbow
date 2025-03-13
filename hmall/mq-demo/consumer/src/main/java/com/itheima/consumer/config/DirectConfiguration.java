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
public class DirectConfiguration {

    // 声明FanoutExchange交换机
    @Bean
    public DirectExchange directExchange(){
        // return ExchangeBuilder.fanoutExchange("hmall.fanout").durable(true).build();
        return new DirectExchange("hmall.direct");
    }

    // 声明第1个队列
    @Bean
    public Queue directQueue1(){
        return QueueBuilder.durable("direct.queue1").build();
        // return new Queue("direct.queue1");
    }

    //绑 定队列1和交换机
    @Bean
    public Binding directBindingQueue1(Queue directQueue1, DirectExchange directExchange){
        // 需要配置多个routingkey
        return BindingBuilder.bind(directQueue1).to(directExchange).with("red");
    }

    // 声明第2个队列
    @Bean
    public Queue directQueue2(){
        // return QueueBuilder.durable("direct.queue2").build();
        return new Queue("direct.queue2");
    }

    @Bean
    public Binding directBindingQueue2(Queue directQueue2, DirectExchange directExchange){
        return BindingBuilder.bind(directQueue2).to(directExchange).with("red");
    }

}

