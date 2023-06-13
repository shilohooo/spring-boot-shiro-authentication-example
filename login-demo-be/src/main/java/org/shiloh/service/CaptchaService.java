package org.shiloh.service;

import java.io.IOException;

/**
 * 验证码 Service
 *
 * @author shiloh
 * @date 2023/6/10 13:37
 */
public interface CaptchaService {
    /**
     * 生成验证码图片
     *
     * @param key 验证码唯一标识
     * @return 验证码图片 Base64 字符串
     * @throws IOException 验证码生成失败抛出的异常
     * @author shiloh
     * @date 2023/6/10 13:39
     */
    String createCaptcha(String key) throws IOException;

    /**
     * 验证码校验
     *
     * @param key     验证码唯一标识
     * @param captcha 验证码
     * @author shiloh
     * @date 2023/6/11 18:34
     */
    void verify(String key, String captcha);
}
