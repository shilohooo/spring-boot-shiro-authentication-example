package org.shiloh.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 参数校验配置
 *
 * @author shiloh
 * @date 2023/6/11 18:09
 */
@Configuration
public class ValidationConfig {
    /**
     * 参数校验器配置，开启 failFast
     *
     * @param beanFactory Spring Bean 工厂实例
     * @author shiloh
     * @date 2023/6/11 18:12
     */
    @Bean
    public Validator validator(AutowireCapableBeanFactory beanFactory) {
        try (ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .constraintValidatorFactory(new SpringConstraintValidatorFactory(beanFactory))
                .buildValidatorFactory()) {
            return validatorFactory.getValidator();
        }
    }
}
