package com.konglk.ims.test;

import com.konglk.common.data.UserInfoDO;
import com.konglk.ims.dao.mongod.MongoUserDao;
import com.konglk.ims.entity.SysUserVO;
import com.konglk.ims.entity.UserVO;
import com.konglk.ims.service.AuthService;
import com.konglk.ims.service.MongoUserService;
import com.konglk.ims.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
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
    @Autowired
    private MongoUserService mongoUserService;

    @Test
    public void testQueryPage() {
        Page<UserInfoDO> userVOS = userService.selectPageUsers(null, 1, 2);
        System.out.println(userVOS);
    }

    @Test
    public void testInsertManagerUser() {
        SysUserVO sysUserVO = new SysUserVO();
        sysUserVO.setUsername("konglingkai");
        sysUserVO.setPassword("konglk");

        authService.register(sysUserVO);
    }

    @Test
    public void testInsertUser() {
        UserVO userVO = new UserVO();
        userVO.setUsername("maomao");
        userVO.setNickname("毛毛");
        userVO.setPwd("maomao");
        userVO.setCellphone("18510396861");
        userVO.setCity("wuhan");
        userVO.setImgUrl("http://39.106.133.40/imgs/maomao.jpg");
        userVO.setSex(1);
        userVO.setCountry("cn");
        userVO.setUserId("62eae00a-6f47-48bb-865e-acb3aa3483ee");

        List<UserVO.Friend> friends = new ArrayList<>();
        UserVO.Friend f1 = new UserVO.Friend();
        f1.setUserId("eb7687c6-da11-4d23-bc71-36c4a12b2247");
        f1.setUsername("konglk");
        f1.setNotename("爸爸");
        f1.setImgUrl("http://39.106.133.40/imgs/konglk.jpg");
        f1.setNickname("千里阵云");
        friends.add(f1);

        UserVO.Friend f2 = new UserVO.Friend();
        f2.setUserId("78ad305d-226e-4155-93e2-357ce376a194");
        f2.setUsername("konglk");
        f2.setNotename("妈妈");
        f2.setImgUrl("http://39.106.133.40/imgs/qintian.jpg");
        f2.setNickname("左耳");
        friends.add(f2);

        userVO.setFriends(friends);
        mongoUserService.insertUser(userVO);
    }
}
