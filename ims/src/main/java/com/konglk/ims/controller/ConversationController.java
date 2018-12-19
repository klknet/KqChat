package com.konglk.ims.controller;

import com.konglk.ims.service.ConversationService;
import com.konglk.ims.service.MongoConversationService;
import com.konglk.ims.service.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by konglk on 2018/10/21.
 */
@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;
    @Autowired
    private MongoConversationService mongoConversationService;

    @GetMapping("/conversationList/{userId}")
    public Object conversationList(@PathVariable String userId) {
        return conversationService.getConversationByUserId(userId);
    }

    @PostMapping("/create")
    public Object create(String userId, String destId) {
        return mongoConversationService.createConversation(userId, destId, null);
    }

    @PostMapping("/open")
    public Object open(String userId, String destId, String conversationId) {
        return mongoConversationService.createConversation(userId, destId, conversationId);
    }

    @GetMapping("/delete")
    public Object delete(String userId, String conversationId) {
        return mongoConversationService.deleteConversation(userId, conversationId);
    }

}
