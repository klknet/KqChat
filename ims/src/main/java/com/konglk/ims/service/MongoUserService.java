package com.konglk.ims.service;

import com.alibaba.fastjson.JSON;
import com.konglk.common.constant.ImsConstants;
import com.konglk.common.data.UserDO;
import com.konglk.ims.dao.mongod.MongoUserDao;
import com.konglk.ims.entity.UserVO;
import com.konglk.ims.enums.UserConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

import java.util.Base64;
import java.util.UUID;

/**
 * Created by konglk on 2018/12/6.
 */
@Service
public class MongoUserService {

    @Autowired
    private MongoUserDao mongoUserDao;
    @Autowired
    private UserService userService;
    @Autowired
    RedisTemplate redisTemplate;

    public void insertUser(UserVO userVO) {
        userVO.setSugar(Base64Utils.encodeToString(UUID.randomUUID().toString().getBytes()));
        String pwd = userVO.getPwd();
        String sugar = userVO.getSugar();
        userVO.setPwd(DigestUtils.md5DigestAsHex((sugar + pwd).getBytes()));
        userVO.setCreatetime(System.currentTimeMillis());
        userVO.setUpdatetime(System.currentTimeMillis());
        userVO.setStatus(UserConfig.UserStatus.NORMAL.v);
        mongoUserDao.save(userVO);
    }

    /*
    用户列表
     */
    public Page<UserVO> userList(UserVO userVO, Pageable pageable) {
        Page<UserVO> users = null;
        if(userVO == null)
            users = mongoUserDao.findAll(pageable);
        else {
            Example<UserVO> example = Example.of(userVO);
            users = mongoUserDao.findAll(example, pageable);
        }
        return users.map(userInfoDO -> {
            userInfoDO.state = userState(userInfoDO.getUserId());
            return userInfoDO;
        });
    }

    /*
    用户详情
     */
    public UserVO userDetail(String userId) {
        return mongoUserDao.findByUsernameOrUserId(userId);
    }

    public UserVO login(String unique, String pwd) {
        if(StringUtils.isAnyEmpty(unique, pwd))
            return null;
        unique = decode(unique, "konglingkai");
        pwd = decode(pwd, "qintiantian");
        UserVO userVO = mongoUserDao.findByUsernameOrUserId(unique);
        if(userVO == null)
            return null;
        String encrptedpwd = DigestUtils.md5DigestAsHex((userVO.getSugar()+pwd).getBytes());
        if(userVO.getPwd().equals(encrptedpwd)) {
            cacheUserState(userVO);
            return userVO;
        }
        return null;
    }

    private void cacheUserState(UserVO userVO) {
        String cert = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        UserDO userDO = new UserDO();
        userDO.userId = userVO.getUserId();
        userDO.state = UserDO.State.ONLINE;
        userDO.certificate = cert;
        redisTemplate.opsForHash().put(ImsConstants.IMS_USER_CERT, userVO.getUserId(), JSON.toJSONString(userDO));
    }

    //解码
    protected String decode(String key, String sugar) {
        Base64.Decoder decoder = Base64.getDecoder();
        String t1 = new String(decoder.decode(key));
        if(t1.startsWith(sugar))
            key = t1.substring(sugar.length());
        return key;
    }

    /*
    用户在线状态
     */
    public UserDO.State userState(String userId) {
        String userDO = (String) redisTemplate.opsForHash().get(ImsConstants.IMS_USER_CERT, userId);
        if(StringUtils.isEmpty(userDO))
            return UserDO.State.OFFLINE;
        return JSON.parseObject(userDO, UserDO.class).state;
    }

    //下线
    public void offline(String userId) {
        redisTemplate.opsForHash().delete(ImsConstants.IMS_USER_CERT, userId);
    }

}
