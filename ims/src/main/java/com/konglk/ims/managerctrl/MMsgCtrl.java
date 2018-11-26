package com.konglk.ims.managerctrl;

import com.konglk.ims.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by konglk on 2018/11/9.
 */
@RestController
@RequestMapping("/manager/msg")
@PreAuthorize("hasRole('USER')")
public class MMsgCtrl {
    @Autowired
    private MsgService msgService;

    @RequestMapping("listMsg")
    public Object listMsg(@RequestParam int page, @RequestParam int size,
                          @RequestParam(required = false) String params) {
        return msgService.selectMsg(null, page, size);
    }
}
