package com.konglk.ims.dao.mongod;

import com.konglk.ims.entity.MsgVO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by konglk on 2018/12/13.
 */
public interface MongoMsgDao extends MongoRepository<MsgVO, String> {

    public MsgVO findByMsgId(String msgId);
}
