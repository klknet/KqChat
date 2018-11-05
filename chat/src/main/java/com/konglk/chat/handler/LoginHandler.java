package com.konglk.chat.handler;

import com.konglk.chat.rest.UserRest;
import com.konglk.chat.server.ClientConnection;
import com.konglk.chat.server.ClientConnectionMap;
import com.konglk.common.constant.ImsConstants;
import com.konglk.common.data.UserDO;
import com.konglk.common.entity.UserVO;
import com.konglk.common.model.Protocol;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by konglk on 2018/8/25.
 */
@Component
public class LoginHandler implements IMessageHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRest userRest;

    /*
    处理登录校验
     */
    @Override
    public void process(ChannelHandlerContext ctx, Protocol.ProtocolMessage message) {
        ClientConnection c = ClientConnectionMap.getClientConnection(ctx.channel().attr(ClientConnection.netIdKey).get());
        Protocol.CLogin m = message.getRequest().getLogin();
        Protocol.ProtocolMessage.Builder msgBuilder = Protocol.ProtocolMessage.newBuilder();
        Protocol.ProtocolMessage.TResponse.Builder responseBuilder = Protocol.ProtocolMessage.TResponse.newBuilder();
        responseBuilder.setRespType(Protocol.ProtocolMessage.RequestType.LOGIN);
        Protocol.SResponse.Builder sResponseBuilder = Protocol.SResponse.newBuilder();
        UserDO userDO = userRest.login(m.getUserId(), m.getPwd());
        if (userDO != null) {
            logger.info("user [" + userDO.userId + "] login sucess");
            ClientConnectionMap.buildSession(c, userDO.userId);
            sResponseBuilder.setCode(ImsConstants.SUCCESS_CODE);
            sResponseBuilder.setCertificate(userDO.certificate);
            sResponseBuilder.setUserId(userDO.userId);
        } else {
            sResponseBuilder.setCode(ImsConstants.FAIL_CODE);
            logger.warn("user [" + m.getUserId() + "] invalid user or password");
        }
        responseBuilder.setResp(sResponseBuilder.build());
        msgBuilder.setResponse(responseBuilder.build());
        Protocol.ProtocolMessage responseMessage = msgBuilder.build();
        ctx.writeAndFlush(responseMessage);
    }

}
