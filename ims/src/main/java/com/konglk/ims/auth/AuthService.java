package com.konglk.ims.auth;

import com.alibaba.fastjson.JSON;
import com.konglk.common.constant.ImsConstants;
import com.konglk.common.data.UserDO;
import com.konglk.common.entity.UserVO;
import com.konglk.common.model.Protocol;
import com.konglk.ims.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by konglk on 2018/8/13.
 */
@Service
public class AuthService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    /*
    验证用户
     */
    public UserDO login(String unique, String pwd) {
        if(StringUtils.isAnyEmpty(unique, pwd))
            return null;
        unique = decode(unique, "konglingkai");
        pwd = decode(pwd, "qintiantian");
        UserVO userVO = userService.selectUser(unique);
        if(userVO == null)
            return null;
        String encrptedpwd = DigestUtils.md5DigestAsHex((userVO.getSugar()+pwd).getBytes());
        if(userVO.getPwd().equals(encrptedpwd)) {
            return userService.cacheUser(userVO);
        }
        return null;
    }

    public boolean isValidMsg(Protocol.CPrivateChat msg) {
        if(msg == null || StringUtils.isEmpty(msg.getDestId()))
            return false;
        return userService.selectUser(msg.getDestId())==null;
    }

    //解码
    private String decode(String key, String sugar) {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            String t1 = new String(decoder.decode(key),"utf8");
            if(t1.startsWith(sugar))
                key = t1.substring(sugar.length());
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return key;
    }

}
