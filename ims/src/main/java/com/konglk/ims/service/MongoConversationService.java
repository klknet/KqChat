package com.konglk.ims.service;

import com.konglk.ims.entity.MsgVO;
import com.konglk.ims.entity.UserVO;
import com.konglk.ims.utils.IdBuilder;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
    更新会话
     */
    protected void updateConversation(String userId, String conversationId, MsgVO msgVO) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("conversations.conversationId").is(conversationId));
        Update update = new Update();
        update.set("conversations.$.lastDate", msgVO.getTs());
        update.set("conversations.$.msgId", msgVO.getMsgId());
        update.set("conversations.$.msgType", msgVO.getMsgType());
        update.set("conversations.$.lastMsg", msgVO.getContent());
        mongoTemplate.updateFirst(query, update, UserVO.class);
    }

    /*
    更新会话最后一条信息
     */
    public void updateConversation(MsgVO msgVO) {
        this.updateConversation(msgVO.getSendId(), msgVO.getConversationId(), msgVO);
        this.updateConversation(msgVO.getDestId(), msgVO.getConversationId(), msgVO);
    }

    /*
    创建会话，如果聊天一方删了会话，新开的窗口保持在原有会话上
     */
    public UserVO createConversation(String userId, String destId, String conversationId) {
        UserVO destVO = mongoUserService.findByUserId(destId);
        UserVO userVO = mongoUserService.findByUserId(userId);
        if(!ObjectUtils.allNotNull(destVO, userVO)){
            throw new IllegalArgumentException();
        }
        UserVO.Conversation conversation = buildConversation(userVO, destVO, conversationId);
        if(StringUtils.isEmpty(conversationId)) {
            UserVO.Conversation destConversation = buildConversation(destVO, userVO, conversation.conversationId);
            updateUserConversation(destConversation, destId);
        }
        return updateUserConversation(conversation, userId);
    }

    /*
    更新用户会话
     */
    protected UserVO updateUserConversation(UserVO.Conversation conversation, String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update();
        update.push("conversations", conversation);
        FindAndModifyOptions findAndModifyOptions = FindAndModifyOptions.options().returnNew(true);
        return mongoTemplate.findAndModify(query, update, findAndModifyOptions, UserVO.class);
    }

    /*
    构建会话
     */
    protected UserVO.Conversation buildConversation(UserVO userVO, UserVO destVO, String conversationId) {
        UserVO.Conversation conversation = new UserVO.Conversation();
        conversation.ts = System.currentTimeMillis();
        if(StringUtils.isEmpty(conversationId)) {
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

}
