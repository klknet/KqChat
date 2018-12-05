package com.konglk.ims.test;

import com.konglk.common.data.UserInfoDO;
import com.konglk.ims.security.SysUserVO;
import com.konglk.ims.service.AuthService;
import com.konglk.ims.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by konglk on 2018/11/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @Test
    public void testQueryPage() {
        Page<UserInfoDO> userVOS = userService.selectPageUsers(null, 1, 2);
        System.out.println(userVOS);
    }

    @Test
    public void testInsertUser() {
        SysUserVO sysUserVO = new SysUserVO();
        sysUserVO.setUsername("konglingkai");
        sysUserVO.setPassword("konglk");

        authService.register(sysUserVO);
    }

    @Test
    public void testStream() {

    }
}
