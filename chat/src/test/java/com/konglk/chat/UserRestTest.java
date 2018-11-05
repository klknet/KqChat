package com.konglk.chat;

import com.konglk.chat.rest.MsgRest;
import com.konglk.chat.rest.UserRest;
import com.konglk.common.data.UserDO;
import com.konglk.common.model.Protocol;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Base64;

/**
 * Created by konglk on 2018/11/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRestTest {

    @Autowired
    private UserRest userRest;
    @Autowired
    private MsgRest msgRest;

    @Test
    public void loginTest() {
        String unique = Base64.getEncoder().encodeToString("konglingkai18062743820".getBytes());
        String pwd = Base64.getEncoder().encodeToString("qintiantiankonglk".getBytes());
        UserDO userDO = userRest.login(unique, pwd);
        System.out.println(userDO);
    }

    @Test
    public void sendFileTest() throws Exception {
        FileInputStream in = null;
        try {
            in = new FileInputStream("d:\\jietu.png");
            byte[] buff = new byte[1024*10];
            in.read(buff);
            String s = msgRest.storeFile(buff, "png");
            System.out.println(s);
        } finally {
            if(in != null)
                in.close();
        }
    }

    @Test
    public void insertMsg() {
        Protocol.CPrivateChat msg = Protocol.CPrivateChat.newBuilder().setExtName("hello").build();
        msgRest.persistMsg(msg);
    }
}
