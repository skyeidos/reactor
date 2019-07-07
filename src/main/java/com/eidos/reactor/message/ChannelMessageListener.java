package com.eidos.reactor.message;

import com.eidos.reactor.websocket.ChannelInfoMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class ChannelMessageListener extends MessageListenerAdapter {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        Long channelId = Long.valueOf(channel.substring("channel-".length()));
        List<WebSocketSession> sessions = ChannelInfoMessageHandler.getSessionMaps().get(channelId);
        if (sessions != null && !sessions.isEmpty()) {
            sessions.forEach(session -> {
                try {
                    session.sendMessage(new TextMessage(message.getBody()));
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            });
        }
    }
}
