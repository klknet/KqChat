package com.konglk.ims.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

/**
 * Created by konglk on 2018/11/10.
 */
public class PageImplWrapper {

    public static <T> Page<T> of(com.github.pagehelper.Page<T> page) {
        if(page.isEmpty())
            return new PageImpl<>(Collections.emptyList());
        Page pageImpl = new PageImpl(page.getResult(), PageRequest.of(page.getPageNum()-1, page.getPageSize()), page.getTotal());
        return pageImpl;
    }

}
