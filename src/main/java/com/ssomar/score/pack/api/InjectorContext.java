package com.ssomar.score.pack.api;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelPipeline;
import lombok.Getter;

/**
 * Context of an injector.
 */
@Getter
public class InjectorContext {

    private final ChannelPipeline pipeline;
    private final ByteBuf message;

    public InjectorContext(ChannelPipeline pipeline, ByteBuf message) {
        this.pipeline = pipeline;
        this.message = message;
    }

}