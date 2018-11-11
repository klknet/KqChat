package com.konglk.ims.test;

import com.konglk.common.data.UserInfoDO;
import com.konglk.ims.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by konglk on 2018/11/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    public void testQueryPage() {
        Page<UserInfoDO> userVOS = userService.selectPageUsers(null, 1, 2);
        System.out.println(userVOS);
    }
}
