package com.konglk.chat.message;

import com.konglk.chat.rest.MsgRest;
import com.konglk.common.model.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by konglk on 2018/11/5.
 */
@Component
public class MessageConsumer {

    @Autowired
    private MsgRest msgRest;

    @Autowired
    private MessageProcessor messageProcessor;

    public void consumeMessage(Protocol.CPrivateChat message) {
        try {
            msgRest.persistMsg(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        messageProcessor.process(message);
    }

}
