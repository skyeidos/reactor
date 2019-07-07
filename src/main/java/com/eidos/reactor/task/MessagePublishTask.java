package com.eidos.reactor.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class MessagePublishTask implements Runnable {


    private ExecutorService service = Executors.newFixedThreadPool(10);

    private final RedisTemplate<String, String> redisTemplate;

    public MessagePublishTask(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Scheduled(cron = "0/1 * * * * *")
    public void publishMessage() {
        for (int i = 0; i < 10; i++) {
            service.execute(this);
        }
    }

    @Override
    public void run() {
        int num = ThreadLocalRandom.current().nextInt(10);
        String channel = "channel-" + num;
        redisTemplate.convertAndSend(channel, "hello world");
    }
}
