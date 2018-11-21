package com.konglk.ims.dao.mongod;

import com.konglk.ims.security.SysUserVO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * Created by konglk on 2018/11/21.
 */
@Component
public interface SysUserDao extends MongoRepository<SysUserVO, String>{

    SysUserVO findByUsername(String username);

}
