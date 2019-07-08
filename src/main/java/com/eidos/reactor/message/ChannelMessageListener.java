package com.eidos.reactor.message;

import com.eidos.reactor.entity.ChannelInfoMessage;
import com.eidos.reactor.service.BroadcastService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.nio.charset.StandardCharsets;

@Slf4j
public class ChannelMessageListener extends MessageListenerAdapter {

    private BroadcastService broadcastService;


    public void setBroadcastService(BroadcastService broadcastService) {
        this.broadcastService = broadcastService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        Long channelId = Long.valueOf(channel.substring("channel-".length()));
        broadcastService.broadcastMessage("/channel/" + channelId
                , ChannelInfoMessage.builder().content(new String(message.getBody(), StandardCharsets.UTF_8)).build());
    }
}
