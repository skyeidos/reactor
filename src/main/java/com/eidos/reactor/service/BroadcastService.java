package com.eidos.reactor.service;

import com.eidos.reactor.entity.ChannelInfoMessage;

public interface BroadcastService {

    void broadcastMessage(String topic, ChannelInfoMessage message);
}
