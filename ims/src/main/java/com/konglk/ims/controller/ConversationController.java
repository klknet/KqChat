package com.konglk.ims.controller;

import com.konglk.ims.service.ConversationService;
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
    private MongoUserService mongoUserService;

    @GetMapping("/conversationList/{userId}")
    public Object conversationList(@PathVariable String userId) {
        return conversationService.getConversationByUserId(userId);
    }

    @PostMapping("/create")
    public Object create(String userId) {
        return mongoUserService.createConversation(userId);
    }

    @PostMapping("/open")
    public Object open(String userId, String conversationId) {
        return mongoUserService.openConversation(conversationId, userId);
    }

}
