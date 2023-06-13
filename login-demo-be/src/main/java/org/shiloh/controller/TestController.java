package org.shiloh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author shiloh
 * @date 2023/6/8 22:35
 */
@RestController
@RequestMapping("/test")
public class TestController {
    /**
     * 测试接口
     *
     * @param name 用户姓名
     * @return 问候语
     * @author shiloh
     * @date 2023/6/8 22:37
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(required = false, defaultValue = "World") String name) {
        return String.format("Hello %s:)", name);
    }
}
