package com.konglk.ims.service;

import com.konglk.ims.aop.ProcessUser;
import com.konglk.ims.entity.MsgVO;
import com.konglk.ims.entity.UserVO;
import com.konglk.ims.utils.IdBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by konglk on 2018/12/17.
 */
@Service
public class MongoConversationService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoUserService mongoUserService;

    /*
    处理会话
     */
    protected void processConversation(MsgVO msgVO, String userId, String destId) {
        UserVO userVO = mongoUserService.findByUserId(userId);
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        Update update = new Update();
        if(userVO.conversations.stream().anyMatch(c -> msgVO.getConversationId().equals(c.conversationId))) {
            query.addCriteria(Criteria.where("conversations.conversationId").is(msgVO.getConversationId()));
            update.set("conversations.$.lastDate", msgVO.getTs());
            update.set("conversations.$.msgId", msgVO.getMsgId());
            update.set("conversations.$.msgType", msgVO.getMsgType());
            update.set("conversations.$.lastMsg", msgVO.getContent());
            mongoTemplate.updateFirst(query, update, UserVO.class);
        } else {
            UserVO destVO = mongoUserService.findByUserId(destId);
            UserVO.Conversation conv = buildConversation(userVO, destVO, msgVO.getTs());
            update.push("conversations", conv);
            mongoTemplate.findAndModify(query, update, UserVO.class);
        }


    }

    /*
    有新消息时，更新会话最后一条信息
     */
    public void updateConversation(MsgVO msgVO) {
        this.processConversation(msgVO, msgVO.getSendId(), msgVO.getDestId());
        this.processConversation(msgVO, msgVO.getDestId(), msgVO.getSendId());
    }

    /*
    创建会话，如果聊天一方删了会话，新开的窗口保持在原有会话上
     */
    @ProcessUser
    public UserVO createConversation(String userId, String destId, String conversationId) {
        UserVO userVO = mongoUserService.findByUserId(userId);
        UserVO destVO = mongoUserService.findByUserId(destId);
        if(!ObjectUtils.allNotNull(destVO, userVO)){
            throw new IllegalArgumentException();
        }
        UserVO.Conversation conversation = buildConversation(userVO, destVO, System.currentTimeMillis());
        return pushConversation(conversation, userId);
    }

    /*
    新加用户会话
     */
    protected UserVO pushConversation(UserVO.Conversation conversation, String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update();
        update.push("conversations", conversation);
        FindAndModifyOptions findAndModifyOptions = FindAndModifyOptions.options().returnNew(true);
        return mongoTemplate.findAndModify(query, update, findAndModifyOptions, UserVO.class);
    }

    /*
    删除会话
     */
    @ProcessUser
    public UserVO deleteConversation(String userId, String conversationId) {
        UserVO userVO = mongoUserService.findByUserId(userId);
        if(userVO != null) {
            return this.pullConversation(this.findConversation(userVO.conversations, conversationId), userId);
        }
        return null;
    }

    protected UserVO pullConversation(UserVO.Conversation conversation, String userId) {
        if(conversation == null) {
            return null;
        }
        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update();
        update.pull("conversations", conversation);
        FindAndModifyOptions findAndModifyOptions = FindAndModifyOptions.options().returnNew(true);
        return mongoTemplate.findAndModify(query, update, findAndModifyOptions, UserVO.class);
    }

    /*
    构建会话,如果对方存在会话，新的会话构建在原有会话之上
     */
    protected UserVO.Conversation buildConversation(UserVO userVO, UserVO destVO, long ts) {
        UserVO.Conversation conversation = new UserVO.Conversation();
        String conversationId = null;
        conversation.ts = ts;
        boolean valid = false;
        for(UserVO.Conversation c : destVO.conversations) {
            if(c.userId.equals(userVO.userId)) {
                valid = true;
                conversation.msgId = c.msgId;
                conversation.msgType = c.msgType;
                conversation.lastMsg = c.lastMsg;
                conversation.lastDate = c.lastDate;
                conversationId = c.conversationId;
                break;
            }
        }
        if(!valid) {
            conversationId = IdBuilder.buildId();
            conversation.lastDate = conversation.ts;//新开会话给当前时间
        }
        conversation.imgUrl = destVO.imgUrl;
        conversation.userId = destVO.userId;
        for(UserVO.Friend f : userVO.friends) {
            if(f.userId.equals(destVO.userId)){
                conversation.notename = f.notename;
                break;
            }
        }
        conversation.conversationId = conversationId;
        return conversation;
    }


    protected boolean existUserAndConversation(String userId, String conversationId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("conversations").in(conversationId));
        return mongoTemplate.exists(query, UserVO.class);
    }



    private UserVO.Conversation findConversation(List<UserVO.Conversation> conversationList, String conversationId) {
        return conversationList.stream().filter(p->conversationId.equals(p.conversationId)).findFirst().get();
    }
}
