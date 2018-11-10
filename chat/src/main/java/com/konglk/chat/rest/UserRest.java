package com.konglk.chat.rest;

import com.konglk.chat.server.ClientConnectionMap;
import com.konglk.common.data.UserDO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by konglk on 2018/11/5.
 */
@Component
public class UserRest extends BaseRest{
    private static Logger logger = LoggerFactory.getLogger(ClientConnectionMap.class);

    /*
    登录
     */
    public UserDO login(String unique, String pwd) {
        if(StringUtils.isAllBlank(unique, pwd))
            return null;
        return restTemplate.getForObject(ims+"/user/login?unique={unique}&pwd={pwd}", UserDO.class, unique, pwd);
    }

    /*
    下线
     */
    public void offline(String userId) {
        try {
            restTemplate.put(ims+"/user/offline?userId={userId}", null, userId);
        } catch (RestClientException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
