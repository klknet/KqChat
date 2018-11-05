package com.konglk.chat.handler;

import com.konglk.common.model.Protocol;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by konglk on 2018/8/25.
 */
public interface IMessageHandler {
    void process(ChannelHandlerContext ctx, Protocol.ProtocolMessage message);
}
