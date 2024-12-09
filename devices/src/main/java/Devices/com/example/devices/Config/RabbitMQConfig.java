package Devices.com.example.devices.Config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfig {

    public static final String DEVICE_EXCHANGE = "device.exchange";

    @Bean
    public Exchange deviceExchange() {
        return new TopicExchange(DEVICE_EXCHANGE);
    }

    @Bean
    public Jackson2JsonMessageConverter deviceMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(deviceMessageConverter());
        return rabbitTemplate;
    }
    @Bean
    public SimpleRabbitListenerContainerFactory deviceInfoListenerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(deviceMessageConverter());
        return factory;
    }



}
