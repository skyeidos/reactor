package com.eidos.reactor.config;

import com.eidos.reactor.message.ChannelMessageListener;
import com.eidos.reactor.service.BroadcastService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class MessageListenerConfig {


    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, BroadcastService broadcastService) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListener(broadcastService), new PatternTopic("channel-*"));
        container.setTaskExecutor(taskExecutor());
        return container;
    }

    @Bean
    public MessageListener messageListener(BroadcastService broadcastService) {
        ChannelMessageListener messageListener = new ChannelMessageListener();
        messageListener.setBroadcastService(broadcastService);
        return messageListener;
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setThreadNamePrefix("message-");
        return taskExecutor;
    }


}
