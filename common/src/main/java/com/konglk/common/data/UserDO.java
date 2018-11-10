package com.konglk.common.data;

import com.konglk.common.entity.UserVO;

/**
 * Created by konglk on 2018/11/5.
 */
public class UserDO {
    public String userId;
    public String username;
    public String certificate;
    public String imgUrl;
    public State state;//0-offline 1-online
    public long ts;//last update time

    public UserDO(){}

    public UserDO(UserVO userVO, State state, String cert) {
        this.userId = userVO.getUserId();
        this.state = state;
        this.certificate = cert;
        this.username = userVO.getUsername();
        this.imgUrl = userVO.getImgUrl();
        this.ts = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "userId='" + userId + '\'' +
                ", certificate='" + certificate + '\'' +
                '}';
    }

    public static enum State{
        ONLINE(0), OFFLINE(1);
        int val;
        private State(int v){
            this.val = v;
        }
    }
}
