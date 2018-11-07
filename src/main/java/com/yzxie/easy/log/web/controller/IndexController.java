package com.yzxie.easy.log.web.controller;

import com.yzxie.easy.log.common.data.ApiRank;
import com.yzxie.easy.log.web.data.ResData;
import com.yzxie.easy.log.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xieyizun
 * @date 2/11/2018 00:10
 * @description:
 */

@RestController
public class IndexController {

    @Autowired
    private IndexService indexService;

    @PostMapping("/log-rpc")
    public ResData logDetail() {
        List<ApiRank> apiRanks = indexService.getTop10VisitedUri("runoobkey");
        return new ResData(apiRanks);
    }

    @GetMapping("/hello")
    public ResData hello() {
        return new ResData("please login first.");
    }

    @GetMapping("/testLogin")
    public ResData testLogin() {
        return new ResData("login ok.");
    }
}
