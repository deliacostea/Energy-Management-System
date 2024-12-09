package com.example.monitoring.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {


    public static final String SENSOR_EXCHANGE = "energyExchange";
    public static final String SENSOR_QUEUE = "energyQueue";
    public static final String SENSOR_ROUTING_KEY = "energyRoutingKey";


    public static final String DEVICE_EXCHANGE = "device.exchange";
    public static final String DEVICE_QUEUE = "deviceInfoQueue";
    public static final String DEVICE_ROUTING_KEY = "device.info";


    @Bean
    public Exchange sensorExchange() {
        return new TopicExchange(SENSOR_EXCHANGE);
    }

    @Bean
    public Queue sensorQueue() {
        return new Queue(SENSOR_QUEUE);
    }

    @Bean
    public Binding sensorBinding() {
        return BindingBuilder.bind(sensorQueue()).to(sensorExchange()).with(SENSOR_ROUTING_KEY).noargs();
    }


    @Bean
    public Exchange deviceExchange() {
        return new TopicExchange(DEVICE_EXCHANGE);
    }

    @Bean
    public Queue deviceQueue() {
        return new Queue(DEVICE_QUEUE);
    }

    @Bean
    public Binding deviceBinding() {
        return BindingBuilder.bind(deviceQueue()).to(deviceExchange()).with(DEVICE_ROUTING_KEY).noargs();
    }


    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.INFERRED);
        return converter;
    }




}
