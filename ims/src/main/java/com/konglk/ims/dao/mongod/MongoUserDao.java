package com.konglk.ims.dao.mongod;

import com.konglk.ims.entity.UserVO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Created by konglk on 2018/12/6.
 */
public interface MongoUserDao extends MongoRepository<UserVO, String> {

    UserVO findByUserId(String userId);

    @Query(value = "{'$or': [{'cellphone': ?0}, {'user_id':?0}]}")
    UserVO findUnique(String unique);


}
