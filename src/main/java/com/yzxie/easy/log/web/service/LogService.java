package com.yzxie.easy.log.web.service;

import com.yzxie.easy.log.common.api.LogConfServiceApi;
import com.yzxie.easy.log.common.data.rpc.LogTypeWithApps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xieyizun
 * @date 18/11/2018 22:42
 * @description:
 */
@Service
public class LogService {
    @Autowired
    private LogConfServiceApi logConfService;

    public List<LogTypeWithApps> listLogTypeWithApps() {
        return logConfService.listLogTypeAndApps();
    }
}
