package com.konglk.chat.handler;

import com.google.protobuf.ByteString;
import com.konglk.chat.config.MqConfig;
import com.konglk.chat.rest.MsgRest;
import com.konglk.common.model.Protocol;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

/**
 * Created by konglk on 2018/9/2.
 */
@Component
public class ChatHandler implements IMessageHandler {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MsgRest msgRest;

    @Override
    public void process(ChannelHandlerContext ctx, Protocol.ProtocolMessage message) {
        Protocol.CPrivateChat chat = message.getRequest().getChat();
        if(true){
            //服务器收到消息后会将消息转发并把这条成功消息推送给发送客户端
            switch (chat.getDataType()){
                case TXT:
                    if(chat.getContent().isEmpty())
                        return;
                    Protocol.CPrivateChat txtChat = Protocol.CPrivateChat.newBuilder().mergeFrom(chat).setTs(System.currentTimeMillis()).build();
                    rabbitTemplate.convertAndSend(MqConfig.MESSAGE_TOPIC, MqConfig.ROUTING_KEY, txtChat);
                    ctx.writeAndFlush(Protocol.ProtocolMessage.newBuilder().setResponse(Protocol.ProtocolMessage.TResponse.newBuilder().
                            setChat(txtChat).setRespType(Protocol.ProtocolMessage.RequestType.CHAT).build()));
                    break;
                case VOICE:
                case IMG:
                    ByteString bytes = chat.getContent();
                    String s = msgRest.storeFile(bytes.toByteArray(), chat.getExtName());
                    try {
                        Protocol.CPrivateChat imgChat =
                                Protocol.CPrivateChat.newBuilder().mergeFrom(chat).
                                        setContent(ByteString.copyFrom(s.getBytes("utf8"))).
                                        setTs(System.currentTimeMillis()).build();
                        rabbitTemplate.convertAndSend(MqConfig.MESSAGE_TOPIC, MqConfig.ROUTING_KEY, imgChat);
                        ctx.writeAndFlush(Protocol.ProtocolMessage.newBuilder().setResponse(Protocol.ProtocolMessage.TResponse.newBuilder().
                                setChat(imgChat).setRespType(Protocol.ProtocolMessage.RequestType.CHAT).build()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    }
}
