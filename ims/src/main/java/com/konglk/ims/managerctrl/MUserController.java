package com.konglk.ims.managerctrl;

import com.konglk.ims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by konglk on 2018/10/31.
 */
@RestController
@RequestMapping("/manager/user")
public class MUserController {

    @Autowired
    private UserService userService;

    @GetMapping("listUser")
    public Object listUser(@RequestParam int page, @RequestParam int size,
                           @RequestParam(required = false) String params) {
        return userService.selectPageUsers(null, page, size);
    }

    @GetMapping("userDetail")
    public Object userDetail(@RequestParam String userId) {
        return userService.selecUserById(userId);
    }

}
