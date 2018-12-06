package com.konglk.ims.dao.mongod;

import com.konglk.ims.entity.UserVO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by konglk on 2018/12/6.
 */
public interface MongoUserDao extends MongoRepository<UserVO, String> {

    UserVO findByUserId(String userId);

    UserVO findByUsernameOrUserId(String unique);
}
