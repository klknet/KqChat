package com.konglk.ims.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.konglk.common.data.UserDO;
import com.konglk.common.data.UserInfoDO;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Created by konglk on 2018/12/6.
 */
@Document(collection = "user")
@JsonIgnoreProperties(value = {"pwd", "id", "sugar, updatetime"})
public class UserVO {
    @Id
    public String id;
    @Indexed(unique = true)
    @Field("user_id")
    public String userId;
    public String username;
    public String nickname;
    public String pwd;
    public String sugar;
    @Field("img_url")
    public String imgUrl;
    @Indexed(unique = true)
    public String cellphone;
    public Integer sex;
    public String country;
    public String city;
    public Long createtime;
    public Integer status;
    public Long updatetime;
    public String signature;
    public List<Friend> friends;
    public List<Conversation> conversations;
    @Transient
    public UserDO.State state;

    public static class Friend {
        @Indexed
        @Field("user_id")
        public String userId;
        public String username;
        public String nickname;
        public String notename;
        @Field("img_url")
        public String imgUrl;
        public Integer sex;
        public String city;
        public String signature;
    }

    public static class Conversation {
        @Indexed
        @Field("conversation_id")
        public String conversationId;
        public Long ts;

        public Conversation() {
        }

        public Conversation(String conversationId, Long ts) {
            this.conversationId = conversationId;
            this.ts = ts;
        }
    }


}
