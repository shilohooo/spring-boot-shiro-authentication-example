package org.shiloh.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码生成配置
 *
 * @author shiloh
 * @date 2023/6/10 13:21
 */
@Configuration
public class KaptchaConfig {

    /**
     * 验证码生成配置
     *
     * @author shiloh
     * @date 2023/6/10 13:36
     */
    @Bean
    public DefaultKaptcha producer() {
        // Properties类
        final Properties properties = new Properties();
        // 图片边框
        properties.setProperty(Constants.KAPTCHA_BORDER, "no");
        // 字体颜色
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        // 字体间隔
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "5");

        final Config config = new Config(properties);
        final DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
