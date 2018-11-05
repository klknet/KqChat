package com.konglk.ims.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.konglk.common.constant.ImsConstants;
import com.konglk.common.entity.ConversationVO;
import com.konglk.common.entity.MsgVO;
import com.konglk.common.model.Protocol;
import com.konglk.ims.enums.MsgConfig;
import com.konglk.ims.mappers.MsgDao;
import com.konglk.ims.utils.IdBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by konglk on 2018/8/24.
 */
@Service
public class MsgService {
    @Autowired
    private MsgDao msgDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ConversationService conversationService;
    @Autowired
    private FastFileStorageClient storageClient;

    public void insertMsg(MsgVO msgVO) {
        msgVO.setMsgId(UUID.randomUUID().toString());
        msgDao.insertMsg(msgVO);
        ConversationVO conversationVO = conversationService.getConversation(msgVO.getSendId(), msgVO.getDestId());
        if(conversationVO != null)
            conversationService.updateLastDate(conversationVO.getConversationId());
    }

    public List<Map<String,Object>> selectHistoryMessageById(String sendId, String destId, String msgId, int pageSize, int direct){
        List<Map<String,Object>> datas =
                msgDao.selectHistoryMessageById(sendId, destId, msgId, pageSize, direct);
        if(CollectionUtils.isEmpty(datas))
            return Collections.EMPTY_LIST;
//        DataProcess.process(datas, new String[]{"createtime"}, new Function[]{DateFormatter::format});
        return datas;

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
        msgVO.setHasRead(MsgConfig.NOT_READ);
        ConversationVO conversation = conversationService.getConversation(msg.getUserId(), msg.getDestId());
        if(conversation != null) {
            msgVO.setConversationId(conversation.getConversationId());
        }
        return msgVO;
    }

    public List<String> selectImagesById(String sendId, String destId) {
        List<String> images = msgDao.selectImagesById(sendId, destId);
        if(CollectionUtils.isEmpty(images))
            return Collections.emptyList();
        return images;
    }

    public void notifyReaded(String userId, String destId) {
        if(userId == null || destId == null)
            return;
        redisTemplate.opsForHash().delete(ImsConstants.IMS_UNREAD_COUNT+userId, destId);
    }

    public void increment(String userId, String destId, long delta) {
        if(userId == null || destId == null)
            return;
        redisTemplate.opsForHash().increment(ImsConstants.IMS_UNREAD_COUNT+userId, destId, delta);
    }

    public long getUnreadCount(String userId, String destId) {
        Object c = redisTemplate.opsForHash().get(ImsConstants.IMS_UNREAD_COUNT+userId, destId);
        if(c==null)
            return 0L;
        try {
            return Long.parseLong(c.toString());
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public String storeFile(MultipartFile multipartFile) {
        try {
            StorePath storePath = storageClient.uploadFile("group1", multipartFile.getInputStream(), multipartFile.getSize(), multipartFile.getOriginalFilename());
            return storePath.getFullPath();
        } catch (IOException e) {
            return "";
        }
    }
}
