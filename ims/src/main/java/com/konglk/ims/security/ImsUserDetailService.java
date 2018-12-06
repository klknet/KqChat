package com.konglk.ims.security;

import com.konglk.ims.dao.mongod.SysUserDao;
import com.konglk.ims.entity.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by konglk on 2018/11/21.
 */
@Service
public class ImsUserDetailService implements UserDetailsService {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserVO userVO = sysUserDao.findByUsername(username);
        if(userVO == null)
            throw new UsernameNotFoundException("username not exists");
        else {
            return JwtSysUserFactory.create(userVO);
        }

    }
}
