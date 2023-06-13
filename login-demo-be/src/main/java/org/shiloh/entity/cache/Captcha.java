package org.shiloh.entity.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shiloh.constant.CaptchaConstants;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * 验证码缓存实体，过期时间为5分钟
 *
 * @author shiloh
 * @date 2023/6/10 14:27
 */
@RedisHash(value = CaptchaConstants.CACHE_NAME, timeToLive = CaptchaConstants.EXPIRATION_TIME_IN_SECONDS)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Captcha implements Serializable {
    private static final long serialVersionUID = -7276285747071491134L;

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 验证码
     */
    private String code;
}
