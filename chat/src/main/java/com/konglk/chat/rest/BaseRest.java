package com.konglk.chat.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by konglk on 2018/11/5.
 */
@Component
public class BaseRest {

    @Value("${ims.prefix}")
    protected String ims;

    @Autowired
    protected RestTemplate restTemplate;

}
