package com.yws.demo.session.replication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author yws
 * @data 2018/10/31
 */
@RestController
public class DemoController {

    /**
     * 获取session Id接口：打印sessionId便于测试效果
     */
    @GetMapping
    public String getSessionId(HttpSession session) {
        return session.getId();
    }
}
