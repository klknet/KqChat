package com.konglk.chat.handler;

import com.konglk.common.model.Protocol;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

/**
 * Created by konglk on 2018/10/29.
 */
@Component
public class HeartbeatHandler implements  IMessageHandler {

    /*
    心跳监测
     */
    @Override
    public void process(ChannelHandlerContext ctx, Protocol.ProtocolMessage message) {
        Protocol.ProtocolMessage.Builder builder = Protocol.ProtocolMessage.newBuilder();
        builder.setResponse(Protocol.ProtocolMessage.TResponse.newBuilder().setRespType(Protocol.ProtocolMessage.RequestType.PONG).build());
        Protocol.ProtocolMessage sResponse = builder.build();
        ctx.writeAndFlush(sResponse);
    }
}
