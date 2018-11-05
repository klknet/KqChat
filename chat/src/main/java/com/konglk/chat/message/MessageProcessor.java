package com.konglk.chat.message;

import com.konglk.chat.rest.MsgRest;
import com.konglk.chat.server.ClientConnection;
import com.konglk.chat.server.ClientConnectionMap;
import com.konglk.common.model.Protocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by konglk on 2018/8/11.
 */
@Component
public class MessageProcessor {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MsgRest msgRest;

    public void process(Protocol.CPrivateChat msg) {
        if (msg == null || StringUtils.isEmpty(msg.getDestId()) || StringUtils.isEmpty(msg.getUserId()))
            return;
        ClientConnection c = ClientConnectionMap.getClientConnection(msg.getDestId());
        msgRest.increment(msg.getDestId(), msg.getUserId(), 1L);
        if (c != null) {
            ChannelHandlerContext ctx = c.getCtx();
            Channel channel = ctx.channel();
            Protocol.ProtocolMessage m = Protocol.ProtocolMessage.newBuilder()
                    .setResponse(Protocol.ProtocolMessage.TResponse.newBuilder().setChat(msg).setRespType(Protocol.ProtocolMessage.RequestType.CHAT).build())
                    .build();
            ChannelFuture channelFuture = channel.writeAndFlush(m);
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    if (!future.isSuccess()) {
                        msgRest.increment(msg.getDestId(), msg.getUserId(), 1L);
                        future.channel().close();
                    }
                }
            });
        }
    }


}
