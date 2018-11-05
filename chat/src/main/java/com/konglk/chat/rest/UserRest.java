package com.konglk.chat.rest;

import com.konglk.common.data.UserDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by konglk on 2018/11/5.
 */
@Component
public class UserRest extends BaseRest{

    /*
    登录
     */
    public UserDO login(String unique, String pwd) {
        if(StringUtils.isAllBlank(unique, pwd))
            return null;
        return restTemplate.getForObject(ims+"/user/login?unique={unique}&pwd={pwd}", UserDO.class, unique, pwd);
    }
}
