package com.yzxie.easy.log.web.controller;

import com.yzxie.easy.log.common.data.rpc.LogTypeWithApps;
import com.yzxie.easy.log.web.data.ResData;
import com.yzxie.easy.log.web.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xieyizun
 * @date 18/11/2018 22:42
 * @description:
 */
@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping("/list-apps")
    public ResData listApps() {
        ResData resData = new ResData();
        List<LogTypeWithApps> logTypeWithAppsList = logService.listLogTypeWithApps();
        resData.setData(logTypeWithAppsList);
        return resData;
    }
}
