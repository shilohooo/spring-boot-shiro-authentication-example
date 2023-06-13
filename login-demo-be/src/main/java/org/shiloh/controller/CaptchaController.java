package org.shiloh.controller;

import lombok.RequiredArgsConstructor;
import org.shiloh.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 验证码接口
 *
 * @author shiloh
 * @date 2023/6/10 13:42
 */
@RestController
@RequestMapping("/captcha")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @Lazy})
public class CaptchaController {
    private final CaptchaService captchaService;

    /**
     * 生成验证码图片
     *
     * @param key 验证码唯一标识
     * @return 验证码图片 Base64 字符串
     * @throws IOException 验证码生成失败抛出的异常
     * @author shiloh
     * @date 2023/6/10 13:39
     */
    @GetMapping("/{key}")
    public String createCaptcha(@PathVariable("key") String key) throws IOException {
        return this.captchaService.createCaptcha(key);
    }
}
