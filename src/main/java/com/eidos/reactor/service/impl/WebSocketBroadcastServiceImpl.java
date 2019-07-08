package com.eidos.reactor.service.impl;

import com.eidos.reactor.entity.ChannelInfoMessage;
import com.eidos.reactor.service.BroadcastService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebSocketBroadcastServiceImpl implements BroadcastService {


    private final SimpMessagingTemplate template;

    public WebSocketBroadcastServiceImpl(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    public void broadcastMessage(String topic, ChannelInfoMessage message) {
        log.info("send message:{} to topic:{}", message, topic);
        template.convertAndSend(topic, message);
    }
}
