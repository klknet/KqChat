package com.konglk.ims.controller;

import com.konglk.common.constant.ImsConstants;
import com.konglk.common.entity.MsgVO;
import com.konglk.common.model.Protocol;
import com.konglk.ims.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Arrays;
import java.util.List;

/**
 * Created by konglk on 2018/10/21.
 */
@RestController
@RequestMapping("/msg")
public class MessageController {
    @Autowired
    private MsgService msgService;

    @GetMapping("/notifyReaded")
    public Object notifyReaded(@RequestParam String userId, @RequestParam String destId) {
        msgService.notifyReaded(userId, destId);
        return ImsConstants.SUCCESS_CODE;
    }

    @GetMapping("/historymessage/{userId}/{destId}/{msgId}/{pageSize}")
    public Object historyMessage(@PathVariable String userId, @PathVariable String destId, @PathVariable String msgId,
                                 @PathVariable int pageSize, @RequestParam("direct") int direct){
        List<Integer> valid = Arrays.asList(1,2,-1,-2);
        if(valid.contains(direct))
            return msgService.selectHistoryMessageById(userId, destId, msgId, pageSize, direct);
        return "";
    }

    @GetMapping("/historymessage/{userId}/{destId}/images")
    public Object messageImages(@PathVariable String userId, @PathVariable String destId) {
        return msgService.selectImagesById(userId, destId);
    }

    /*
    持久化消息
     */
    @PostMapping(value = "/persistMsg", consumes = "application/x-protobuf")
    public void persistMsg(@RequestBody Protocol.CPrivateChat msg) {
        MsgVO msgVO = msgService.buildMsg(msg);
        msgService.insertMsg(msgVO);
    }

    /*
    未读消息+1
     */
    @PutMapping("/increment")
    public void increment(@RequestParam String userId, @RequestParam String destId, @RequestParam Long delta) {
        msgService.increment(userId, destId, delta);
    }

    @PostMapping("/storeFile")
    public Object storeFile(MultipartHttpServletRequest request) {
        MultipartFile multipartFile = request.getFile("file");
        return msgService.storeFile(multipartFile);
    }

}
