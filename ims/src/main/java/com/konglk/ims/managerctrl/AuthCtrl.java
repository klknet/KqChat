package com.konglk.ims.managerctrl;

import com.konglk.ims.security.ImsUserDetailService;
import com.konglk.ims.service.AuthService;
import com.konglk.ims.security.JwtAuthenticationRequest;
import com.konglk.ims.security.JwtAuthenticationResponse;
import com.konglk.ims.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by konglk on 2018/11/21.
 */
@RestController
@RequestMapping("/auth")
public class AuthCtrl {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    AuthService authService;

    @Autowired
    private ImsUserDetailService userDetailsService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) {
        final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token, authenticationRequest.getUsername()));
    }

    @GetMapping("logout")
    public void logout() {

    }

    @GetMapping(value = "/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken, authService.getUsername(refreshedToken)));
        }
    }

    @GetMapping(value = "/isValid")
    public ResponseEntity isValid(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        return authService.validToken(token) ? ResponseEntity.ok(null) : ResponseEntity.badRequest().body(null);
    }

}
