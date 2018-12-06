package com.konglk.ims.entity;

import com.konglk.common.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * Created by konglk on 2018/11/21.
 */
@Document(collection = "sys_user")
public class SysUserVO extends BaseEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;

    @Field("last_pwd_reset_date")
    private Date lastPwdResetDate;

    private List<String> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastPwdResetDate() {
        return lastPwdResetDate;
    }

    public void setLastPwdResetDate(Date lastPwdResetDate) {
        this.lastPwdResetDate = lastPwdResetDate;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
