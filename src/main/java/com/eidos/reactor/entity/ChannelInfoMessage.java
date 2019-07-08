package com.eidos.reactor.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelInfoMessage {
    private String content;
}
