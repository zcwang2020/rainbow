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
public class FanoutConfiguration {

    // 声明FanoutExchange交换机
    @Bean
    public FanoutExchange fanoutExchange(){
        // return ExchangeBuilder.fanoutExchange("hmall.fanout").durable(true).build();
        return new FanoutExchange("hmall.fanout");
    }

    // 声明第1个队列
    @Bean
    public Queue fanoutQueue1(){
        return QueueBuilder.durable("fanout.queue1").build();
        // return new Queue("fanout.queue1");
    }

    // 声明第2个队列
    @Bean
    public Queue fanoutQueue2(){
        // return QueueBuilder.durable("fanout.queue2").build();
        return new Queue("fanout.queue2");
    }

    //绑 定队列1和交换机
    @Bean
    public Binding fanoutBindingQueue1(Queue fanoutQueue1, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    @Bean
    public Binding fanoutBindingQueue2(Queue fanoutQueue2, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }
}

