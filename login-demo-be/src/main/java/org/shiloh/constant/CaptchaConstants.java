package org.shiloh.constant;

/**
 * 验证码相关常量
 *
 * @author shiloh
 * @date 2023/6/10 14:38
 */
public final class CaptchaConstants {
    private CaptchaConstants() {
    }

    /**
     * 缓存名称
     */
    public static final String CACHE_NAME = "Captcha";

    /**
     * 缓存有效时间：五分钟
     */
    public static final long EXPIRATION_TIME_IN_SECONDS = 60 * 5;

}
