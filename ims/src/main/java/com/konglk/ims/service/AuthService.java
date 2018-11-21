package com.konglk.ims.service;

import com.konglk.ims.security.SysUserVO;

public interface AuthService {
    SysUserVO register(SysUserVO userToAdd);
    String login(String username, String password);
    String refresh(String oldToken);
}
