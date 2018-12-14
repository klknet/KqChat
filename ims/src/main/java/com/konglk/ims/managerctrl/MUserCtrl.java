package com.konglk.ims.managerctrl;

import com.konglk.ims.entity.UserVO;
import com.konglk.ims.service.MongoUserService;
import com.konglk.ims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by konglk on 2018/10/31.
 */
@RestController
@RequestMapping("/manager/user")
@PreAuthorize("hasRole('USER')")
public class MUserCtrl {

    @Autowired
    private UserService userService;
    @Autowired
    private MongoUserService mongoUserService;

    @GetMapping("/listUser")
    public Object listUser(@RequestParam int page, @RequestParam int size,
                           @RequestParam(required = false) String params) {
//        return userService.selectPageUsers(null, page, size);
        return mongoUserService.userList(null, PageRequest.of(page, size, Sort.by(Sort.Order.desc("updatetime"))));
    }

    @GetMapping("userDetail")
    public Object userDetail(@RequestParam String userId) {
        return mongoUserService.userDetail(userId);
    }

    @PostMapping("userUpdate")
    public void userUpdate(@RequestBody UserVO userVO) {
        mongoUserService.userUpdate(userVO);
    }

}
