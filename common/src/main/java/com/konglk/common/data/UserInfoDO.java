package com.konglk.common.data;

/**
 * Created by konglk on 2018/11/10.
 */
public class UserInfoDO {
    public String userId;
    public String username;
    public String nickname;
    public String imgUrl;
    public String cellphone;
    public Integer sex;
    public String city;
    public Long createtime;
    public Integer status;
    public UserDO.State state;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setState(UserDO.State state) {
        this.state = state;
    }
}
