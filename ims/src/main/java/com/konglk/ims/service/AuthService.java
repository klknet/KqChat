package com.konglk.ims.service;

import com.konglk.ims.security.SysUserVO;

public interface AuthService {
    SysUserVO register(SysUserVO userToAdd);
    String login(String username, String password);
    void logout();
    String refresh(String oldToken);
    boolean validToken(String token);
    String getUsername(String token);
}
