package com.konglk.ims.security;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    private final String user;

    public JwtAuthenticationResponse(String token, String user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }

    public String getUser() {
        return user;
    }
}