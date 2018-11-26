package com.konglk.ims.service;

import com.konglk.ims.dao.mongod.SysUserDao;
import com.konglk.ims.security.ImsUserDetailService;
import com.konglk.ims.security.JwtSysUser;
import com.konglk.ims.security.SysUserVO;
import com.konglk.ims.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

import static java.util.Arrays.asList;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ImsUserDetailService userDetailsService;
    @Autowired
    private JwtTokenUtils jwtTokenUtil;
    @Autowired
    private SysUserDao userRepository;
    @Autowired
    BCryptPasswordEncoder encoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    public SysUserVO register(SysUserVO userToAdd) {
        final String username = userToAdd.getUsername();
        if(userRepository.findByUsername(username)!=null) {
            return null;
        }
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setLastPwdResetDate(new Date());
        userToAdd.setRoles(asList("ROLE_USER"));
        return userRepository.insert(userToAdd);
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public void logout() {
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtSysUser userDetails = (JwtSysUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, userDetails.getLastPwdResetDate())){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    @Override
    public boolean validToken(String token) {
        if(StringUtils.isEmpty(token))
            return false;
        if(token.startsWith(tokenHead)) {
            return jwtTokenUtil.validToken(token.substring(tokenHead.length()), null);
        }
        return false;
    }

    @Override
    public String getUsername(String token) {
        if(StringUtils.isEmpty(token))
            return null;
        if(token.startsWith(tokenHead)) {
            return jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        }
        return null;
    }
}