package com.konglk.ims.controller;

import com.github.pagehelper.PageInfo;
import com.konglk.common.entity.UserData;
import com.konglk.common.entity.UserVO;
import com.konglk.ims.auth.AuthService;
import com.konglk.ims.service.RelationshipService;
import com.konglk.ims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by konglk on 2018/8/25.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @Autowired
    private RelationshipService relationshipService;

    @GetMapping("/login")
    public Object login(@RequestParam String unique, @RequestParam String pwd) {
        return authService.login(unique, pwd);
    }

    @GetMapping("/profile/{userId}/{certificate}")
    public Object userProfile(@PathVariable("userId")String userId, @PathVariable("certificate")String certificate) {
        Map<String,Object> userVO = userService.selecUserById(userId);
        return userVO;
    }

    @GetMapping
    public Object selectPageUsers(@RequestParam Map<String,Object> params, @RequestParam Integer pageNo, @RequestParam(required = false) Integer pageSize){
        PageInfo<UserVO> usersPage = userService.selectPageUsers(params, pageNo, pageSize);
        return usersPage;
    }

    /**
     * 查询好友列表
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/relationships")
    public Object findUserRelationships(@PathVariable("userId")String userId) {
        Map<String, List<UserData>> relationshipMap = relationshipService.selectRelationshipByUserId(userId);

        return !relationshipMap.isEmpty() ? relationshipMap : Collections.emptyMap();
    }

    /**
     * 查询新朋友
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/relationships/new")
    public Object findUserNewFriends(@PathVariable("userId")String userId) {
        List<UserData> newFriendsList = relationshipService.selectNewFriendsByUserId(userId);

        return !newFriendsList.isEmpty() ? newFriendsList : Collections.emptyList();
    }

    /**
     * 添加好友
     * @param userId
     * @param toUser
     * @return
     */
    @PostMapping("/{userId}/relationships")
    public Object insertRelationship(@PathVariable("userId") String userId, String toUser) {
        relationshipService.insertRelationship(userId, toUser);
        return "";
    }

    /**
     * 通过好友验证
     * @param userId
     * @param fromUser
     * @return
     */
    @PostMapping("/{userId}/relationships/{fromUser}/pass")
    public Object passRelationship(@PathVariable("userId") String userId, @PathVariable("fromUser") String fromUser){
        return this.relationshipService.passRelationship(fromUser, userId);
    }

    /**
     * 删除好友
     * @param userId
     * @param toUser
     * @return
     */
    @DeleteMapping("/{userId}/relationships/{toUser}/delete")
    public Object delRelationship(@PathVariable("userId") String userId, @PathVariable("toUser") String toUser){
        this.relationshipService.delRelationship(userId, toUser);
        return "";
    }

    /**
     * 查询好友详情
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/relationships/{toUser}/detail")
    public Object getFriendDetail(@PathVariable("userId")String userId, @PathVariable("toUser") String toUser) {
        Map<String, Object> friend = userService.selecUserById(toUser);
        return friend;
    }

    @GetMapping("/test")
    public Object test() { return "hello world"; }
}
