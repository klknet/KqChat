package com.konglk.ims.aop;

import com.konglk.ims.entity.UserVO;
import com.konglk.ims.service.MsgService;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Created by konglk on 2018/12/19.
 */
@Aspect
@Component
public class UserAspect {
    @Autowired
    private MsgService msgService;

    @Pointcut("@annotation(com.konglk.ims.aop.ProcessUser)")
    public void postProcessUser(){}

    @AfterReturning(pointcut = "postProcessUser()", returning = "userVO")
    public void processUser(JoinPoint joinPoint, UserVO userVO) {
        if(CollectionUtils.isNotEmpty(userVO.conversations)) {
            userVO.conversations.forEach(c->{
                c.unreadCount = msgService.getUnreadCount(userVO.userId, c.userId);
            });
            Collections.reverse(userVO.conversations);
        }
    }


}
