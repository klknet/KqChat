package com.konglk.chat.rest;

import com.konglk.common.model.Protocol;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Created by konglk on 2018/11/5.
 */
@Component
public class MsgRest extends BaseRest {

    /*
    持久化消息
     */
    public void persistMsg(Protocol.CPrivateChat msg) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-protobuf");
        HttpEntity entity = new HttpEntity(msg, headers);
        restTemplate.postForObject(ims+"/msg/persistMsg", entity, Void.class);
    }

    /*
    未读消息统计，+1
     */
    public void increment(String userId, String destId, Long delta) {
        restTemplate.put(ims+"/msg/increment?userId={userId}&destId={destId}&delta={delta}", null, userId, destId, delta);
    }

    public String storeFile(byte[] buff, String extName) {
        ByteArrayResource resource = new ByteArrayResource(buff){
            @Override
            public String getFilename() {
                return extName;
            }
        };
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("file", resource);
        String s = restTemplate.postForObject(ims + "/msg/storeFile", params, String.class, extName);
        System.out.println(s);
        return s;
    }
}

