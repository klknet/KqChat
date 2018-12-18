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
        userVO.username= ("konglk");
        userVO.nickname = ("千里阵云");
        userVO.pwd = ("konglk");
        userVO.cellphone = ("18062743820");
        userVO.city = ("wuhan");
        userVO.imgUrl =("http://39.106.133.40/imgs/konglk.jpg");
        userVO.sex = (1);
        userVO.country = ("cn");
        userVO.userId = ("eb7687c6-da11-4d23-bc71-36c4a12b2247");
        userVO.signature = "巧者劳而智者忧";

        List<UserVO.Friend> friends = new ArrayList<>();

        UserVO.Friend f2 = new UserVO.Friend();
        f2.userId = ("78ad305d-226e-4155-93e2-357ce376a194");
        f2.username = ("qintian");
        f2.notename = ("老婆");
        f2.imgUrl = ("http://39.106.133.40/imgs/qintian.jpg");
        f2.nickname = ("左耳");
        friends.add(f2);

        userVO.friends = (friends);
        mongoUserService.insertUser(userVO);
    }

    @Test
    public void testSel() {
        UserVO userVO = mongoUserService.userDetail("18062743820");
        System.out.println(userVO);
    }
}
