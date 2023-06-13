package org.shiloh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 *
 * @author shiloh
 * @date 2023/6/8 22:12
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"org.shiloh.dao"})
@EnableRedisRepositories(basePackages = {"org.shiloh.dao"})
@EnableJpaAuditing
@EnableTransactionManagement
public class WebApp {
    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }
}
