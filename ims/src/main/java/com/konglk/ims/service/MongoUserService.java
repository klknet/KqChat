package com.konglk.ims.service;

import com.alibaba.fastjson.JSON;
import com.konglk.common.constant.ImsConstants;
import com.konglk.common.data.UserDO;
import com.konglk.ims.dao.mongod.MongoUserDao;
import com.konglk.ims.entity.UserVO;
import com.konglk.ims.enums.UserConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
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
    private MongoTemplate mongoTemplate;

    @Autowired
    private MsgService msgService;
    @Autowired
    RedisTemplate redisTemplate;

    public void insertUser(UserVO userVO) {
        userVO.sugar = Base64Utils.encodeToString(UUID.randomUUID().toString().getBytes());
        String pwd = userVO.pwd;
        String sugar = userVO.sugar;
        userVO.pwd = DigestUtils.md5DigestAsHex((sugar + pwd).getBytes());
        userVO.createtime = System.currentTimeMillis();
        userVO.updatetime = System.currentTimeMillis();
        userVO.status = UserConfig.UserStatus.NORMAL.v;
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
            userInfoDO.state = userState(userInfoDO.userId);
            return userInfoDO;
        });
    }

    /*
    用户详情
     */
    public UserVO userDetail(String userId) {
        UserVO userVO = mongoUserDao.findByUserId(userId);
        Assert.notNull(userVO, "not exist user id");
        if(CollectionUtils.isNotEmpty(userVO.conversations)) {
            userVO.conversations.forEach(c->{
                c.unreadCount = msgService.getUnreadCount(userId, c.userId);
            });
        }
        return userVO;
    }

    public void userUpdate(UserVO userVO) {
        if(StringUtils.isEmpty(userVO.userId))
            return;
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userVO.userId));
        UserVO user = mongoTemplate.findOne(query, UserVO.class);
        user.city = userVO.city;
        user.nickname = userVO.nickname;
        user.updatetime = System.currentTimeMillis();
        mongoTemplate.save(user);
    }

    public UserDO login(String unique, String pwd) {
        if(StringUtils.isAnyEmpty(unique, pwd))
            return null;
        unique = decode(unique, "konglingkai");
        pwd = decode(pwd, "qintiantian");
        UserVO userVO = mongoUserDao.findUnique(unique);
        if(userVO == null)
            return null;
        String encrptedpwd = DigestUtils.md5DigestAsHex((userVO.sugar+pwd).getBytes());
        if(userVO.pwd.equals(encrptedpwd)) {
            return cacheUserState(userVO);
        }
        return null;
    }

    private UserDO cacheUserState(UserVO userVO) {
        String cert = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        UserDO userDO = new UserDO();
        userDO.userId = userVO.userId;
        userDO.state = UserDO.State.ONLINE;
        userDO.certificate = cert;
        redisTemplate.opsForHash().put(ImsConstants.IMS_USER_CERT, userVO.userId, JSON.toJSONString(userDO));
        return userDO;
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


    public UserVO findByUserId(String userId) {
        return mongoUserDao.findByUserId(userId);
    }
}
