package com.konglk.ims.service;

import com.github.pagehelper.PageHelper;
import com.konglk.common.constant.ImsConstants;
import com.konglk.common.data.UserDO;
import com.konglk.common.data.UserInfoDO;
import com.konglk.common.entity.UserVO;
import com.konglk.ims.enums.UserConfig;
import com.konglk.ims.mappers.UserDao;
import com.konglk.ims.utils.EncryptUtils;
import com.konglk.ims.utils.PageImplWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.*;

/**
 * Created by konglk on 2018/8/24.
 */
@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UserDao userDao;
    @Autowired
    RedisTemplate redisTemplate;

    public void insertUser(UserVO userVO) {
        userVO.setSugar(Base64Utils.encodeToString(UUID.randomUUID().toString().getBytes()));
        String pwd = userVO.getPwd();
        String sugar = userVO.getSugar();
        userVO.setPwd(EncryptUtils.crypt(sugar + pwd));
        userVO.setCreatetime(System.currentTimeMillis());
        userVO.setUpdatetime(System.currentTimeMillis());
        userVO.setStatus(UserConfig.UserStatus.NORMAL.v);
        userDao.insertUser(userVO);
    }

    public UserVO selectUser(String unique) {
        return userDao.selectUser(unique);
    }

    /*
    修改密码
     */
    public void updatePwd(String newpwd, String unique) {
        UserVO userVO = userDao.selectUser(unique);
        if (userVO != null) {
            String encrptpwd = EncryptUtils.crypt(userVO.getSugar() + newpwd);
            userDao.updatePwd(encrptpwd, unique);
        }
    }

    public Map<String, Object> selectUserById(String userId) {
        return userDao.selectUserById(userId);
    }

    /*
    分页查找用户
     */
    public Page<UserInfoDO> selectPageUsers(Map<String, Object> params, Integer pageNo, Integer pageSize) {
        com.github.pagehelper.Page<UserInfoDO> userVOS = PageHelper.startPage(pageNo, pageSize).doSelectPage(() -> userDao.selectUsersByPage(null));
        Page<UserInfoDO> userPages = PageImplWrapper.of(userVOS);
        return userPages.map(userInfoDO -> {
            userInfoDO.state = userState(userInfoDO.userId);
            return userInfoDO;
        });
    }

    /*
    更新用户
     */
    public void userUpdate(UserVO userVO) {
        if(StringUtils.isEmpty(userVO.getUserId()))
            return;
        userDao.updateUserInfo(userVO);
    }

    /*
    用户在线状态
     */
    public UserDO.State userState(String userId) {
        UserDO userDO = (UserDO) redisTemplate.opsForHash().get(ImsConstants.IMS_USER_CERT, userId);
        if(userDO == null)
            return UserDO.State.OFFLINE;
        return userDO.state;
    }

    /*
    批量用户接口
     */
    public List<UserDO> multiUserState(List<String> userIds) {
        HashOperations<String, String, UserDO> ops = redisTemplate.opsForHash();
        List<UserDO> list = ops.multiGet(ImsConstants.IMS_USER_CERT, userIds);
        return list;
    }

    /*
    缓存用户登录信息
     */
    public UserDO cacheUser(UserVO userVO) {
        String cert = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        UserDO userDO = new UserDO(userVO, UserDO.State.ONLINE, cert);
        // online
        redisTemplate.opsForHash().put(ImsConstants.IMS_USER_CERT, userVO.getUserId(), userDO);
        return userDO;
    }

    //下线
    public void offline(String userId) {
        redisTemplate.opsForHash().delete(ImsConstants.IMS_USER_CERT, userId);
    }


}
