package com.konglk.common.data;

import com.konglk.common.entity.UserVO;

/**
 * Created by konglk on 2018/11/5.
 */
public class UserDO {
    public String userId;
    public String certificate;

    public UserDO(){}

    public UserDO(String userId, String cert) {
        this.userId = userId;
        this.certificate = cert;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "userId='" + userId + '\'' +
                ", certificate='" + certificate + '\'' +
                '}';
    }
}
