package com.konglk.ims.security;

import com.konglk.ims.entity.SysUserVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by konglk on 2018/11/21.
 */
public class JwtSysUserFactory {

    public static JwtSysUser create(SysUserVO userVO) {
        return new JwtSysUser(userVO.getId(), userVO.getUsername(), userVO.getPassword(), userVO.getLastPwdResetDate(), map2Authority(userVO.getRoles()));
    }

    private static List<? extends GrantedAuthority> map2Authority(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

}
