package com.konglk.ims.service;

import com.konglk.common.entity.ConversationVO;
import com.konglk.common.model.Protocol;
import com.konglk.ims.dao.mongod.MongoMsgDao;
import com.konglk.ims.entity.MsgVO;
import com.konglk.ims.entity.UserVO;
import com.konglk.ims.enums.MsgConfig;
import com.konglk.ims.utils.IdBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

/**
 * Created by konglk on 2018/12/13.
 */
@Service
public class MongoMsgService {
    @Autowired
    private MongoMsgDao mongoMsgDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoUserService mongoUserService;
    @Autowired
    private MongoConversationService mongoConversationService;

    public void insertMsg(MsgVO msgVO) {
        msgVO.setMsgId(IdBuilder.buildId());
        mongoMsgDao.save(msgVO);
        mongoConversationService.updateConversation(msgVO);
    }

    public MsgVO buildMsg(Protocol.CPrivateChat msg) {
        MsgVO msgVO = new MsgVO();
        try {
            msgVO.setContent(new String(msg.getContent().toByteArray(), "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        msgVO.setSendId(msg.getUserId());
        msgVO.setDestId(msg.getDestId());
        msgVO.setMsgId(IdBuilder.buildId());
        msgVO.setMsgType(msg.getDataType().getNumber());
        msgVO.setTs(msg.getTs()==0 ? System.currentTimeMillis() : msg.getTs());
        msgVO.setConversationId(msg.getConversationId());
        return msgVO;
    }

    public List<MsgVO> messages(String userId, String conversationId, String msgId, int self) {
        MsgVO msgVO = mongoMsgDao.findByMsgId(msgId);
        UserVO userVO = mongoUserService.findByUserId(userId);
        if(msgVO == null || userVO == null) {
            throw new IllegalArgumentException("not exist message or user");
        }
        UserVO.Conversation start = userVO.conversations.stream().filter(c->c.conversationId.equals(conversationId)).findFirst().get();
        Query query = new Query();
        query.with(PageRequest.of(0, 20, Sort.by(Sort.Order.desc("ts"))));
        Criteria criteria = Criteria.where("ts").gte(start.ts);
        criteria = self == 0 ? criteria.lt(msgVO.getTs()) : criteria.lte(msgVO.getTs());
        query.addCriteria(criteria);
        query.addCriteria(Criteria.where("conversationId").is(conversationId));
        return mongoTemplate.find(query, MsgVO.class);
    }



}
