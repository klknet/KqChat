package com.konglk.ims.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by konglk on 2018/12/12.
 */
@Document
public class ConversationVO {
    @Id
    private String id;

    @Indexed
    @Field("conversation_id")
    private String conversationId;

    @Indexed
    @Field("user_id")
    private String userId;

    @Indexed
    @Field("dest_id")
    private String destId;

    private Long ts;

    @Field("last_date")
    private Long lastDate; //最后一条消息时间


}
