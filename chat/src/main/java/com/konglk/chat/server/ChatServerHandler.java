package com.konglk.chat.server;

import com.konglk.chat.message.MessageDispatcher;
import com.konglk.chat.utils.SpringUtils;
import com.konglk.common.model.Protocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by konglk on 2018/11/5.
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<Protocol.ProtocolMessage> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private MessageDispatcher messageDispatcher = SpringUtils.getBean(MessageDispatcher.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("active a new channel " + ctx.channel().remoteAddress());
        ClientConnectionMap.addClientConnection(ctx);
    }

    /*
    超时踢掉连接
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        IdleStateEvent event = (IdleStateEvent) evt;
        logger.info(ctx.channel().remoteAddress()+" expired: "+event.state());
        ctx.channel().close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Protocol.ProtocolMessage msg) throws Exception {
        try {
            messageDispatcher.dispatch(ctx, msg);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("remove a channel " + ctx.channel().remoteAddress());
        ClientConnectionMap.removeClientConnection(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.channel().close();
    }
}
