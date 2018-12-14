package com.konglk.ims.service;

import com.konglk.ims.dao.mongod.MongoMsgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by konglk on 2018/12/13.
 */
@Service
public class MongoMsgService {
    @Autowired
    private MongoMsgDao mongoMsgDao;
    @Autowired
    private MongoTemplate mongoTemplate;


}
