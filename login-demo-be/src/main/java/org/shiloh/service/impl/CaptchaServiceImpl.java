package org.shiloh.service.impl;

import com.google.code.kaptcha.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.shiloh.common.exception.BizException;
import org.shiloh.constant.CommonConstants;
import org.shiloh.dao.CaptchaDao;
import org.shiloh.entity.cache.Captcha;
import org.shiloh.exception.CaptchaBizExceptionEnum;
import org.shiloh.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * 验证码 ServiceImpl
 *
 * @author shiloh
 * @date 2023/6/10 13:40
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @Lazy})
@Slf4j
public class CaptchaServiceImpl implements CaptchaService {
    private final Producer producer;
    private final CaptchaDao captchaDao;

    /**
     * 生成验证码图片
     *
     * @param key 验证码唯一标识
     * @return 验证码图片 Base64 字符串
     * @throws IOException 验证码生成失败抛出的异常
     * @author shiloh
     * @date 2023/6/10 13:39
     */
    @Override
    public String createCaptcha(String key) throws IOException {
        // 生成5位随机数验证码
        final String captcha = RandomStringUtils.random(5, false, true);
        log.info("验证码唯一标识：{}，验证码：{}", key, captcha);
        this.captchaDao.save(new Captcha(key, captcha));
        final BufferedImage bufferedImage = this.producer.createImage(captcha);
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", outputStream);
            final String result = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            return String.format("%s%s", CommonConstants.IMG_BASE64_PREFIX, result);
        }
    }

    /**
     * 验证码校验
     *
     * @param key     验证码唯一标识
     * @param captcha 验证码
     * @author shiloh
     * @date 2023/6/11 18:34
     */
    @Override
    public void verify(String key, String captcha) {
        final Captcha cachedCaptcha = this.captchaDao.findById(key)
                .orElseThrow(() -> new BizException(CaptchaBizExceptionEnum.EXPIRED_CAPTCHA));
        if (!captcha.equals(cachedCaptcha.getCode())) {
            throw new BizException(CaptchaBizExceptionEnum.INCORRECT_CAPTCHA);
        }
        // 校验通过了，删掉验证码，防止重复使用
        this.captchaDao.deleteById(key);
    }
}
