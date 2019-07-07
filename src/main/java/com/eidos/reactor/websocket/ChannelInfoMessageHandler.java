package com.eidos.reactor.websocket;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ChannelInfoMessageHandler extends TextWebSocketHandler {



    private static Map<Long, List<WebSocketSession>> sessionMaps = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Long channelId = Long.valueOf(message.getPayload());
        if (sessionMaps.get(channelId) == null) {
            sessionMaps.put(channelId, Lists.newArrayList(session));
        } else {
            sessionMaps.get(channelId).add(session);
        }
        session.sendMessage(new TextMessage("server received:," + channelId));
    }


    public static Map<Long, List<WebSocketSession>> getSessionMaps() {
        return Collections.unmodifiableMap(sessionMaps);
    }
}
