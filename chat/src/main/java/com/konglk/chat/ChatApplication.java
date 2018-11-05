package com.konglk.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by konglk on 2018/11/5.
 */
@SpringBootApplication
public class ChatApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ChatApplication.class).web(WebApplicationType.NONE).run(args);
    }
}
