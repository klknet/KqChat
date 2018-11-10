package com.konglk.ims.auth;

import com.konglk.ims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by konglk on 2018/8/25.
 */
@Component
public class AuthFilter extends GenericFilterBean {

    @Autowired
    UserService userService;

    @Override
    protected void initFilterBean() throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String userId = httpRequest.getHeader("userId");
        String certificate = httpRequest.getHeader("certificate");
        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(certificate)){
            return;
        }
        if(userService.isValidUser(userId, certificate)){
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
