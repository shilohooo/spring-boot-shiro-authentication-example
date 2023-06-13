package org.shiloh.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.shiloh.security.codec.PasswordEncryptUtils;
import org.shiloh.security.filter.MyAuthenticationFilter;
import org.shiloh.security.realm.MyShiroRealm;
import org.shiloh.security.session.MyShiroSessionManager;
import org.shiloh.service.SysUserService;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro 配置
 *
 * @author shiloh
 * @date 2023/6/8 22:41
 */
@Configuration
public class ShiroConfig {
    /**
     * 全局会话超时时间，单位：毫秒
     */
    public static final int GLOBAL_SESSION_TIMEOUT_MS = 1000 * 60 * 30;

    /**
     * 会话定时校验间隔，单位：毫秒
     */
    public static final int SESSION_VALIDATION_INTERVAL_MS = 1000 * 30;

    /**
     * 注入 Shiro Realm
     *
     * @author shiloh
     * @date 2023/6/8 22:42
     */
    @Bean("authorizer")
    public MyShiroRealm myShiroRealm(SysUserService sysUserService) {
        final MyShiroRealm myShiroRealm = new MyShiroRealm(sysUserService);
        // 设置密码匹配器
        final HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // Hash 算法
        credentialsMatcher.setHashAlgorithmName(PasswordEncryptUtils.ALGORITHM_NAME);
        // Hash 迭代次数
        credentialsMatcher.setHashIterations(PasswordEncryptUtils.HASH_ITERATIONS);
        // 以 16 进制存储密码
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        myShiroRealm.setCredentialsMatcher(credentialsMatcher);
        return myShiroRealm;
    }

    /**
     * 注入 Shiro Filter
     *
     * @author shiloh
     * @date 2023/6/8 22:52
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(ObjectMapper objectMapper, SecurityManager securityManager) {
        final ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        // 自定义过滤器设置
        final Map<String, Filter> filterMap = new HashMap<>(1);
        filterMap.put(DefaultFilter.authc.name(), new MyAuthenticationFilter(objectMapper));
        factoryBean.setFilters(filterMap);
        // 必须是有序 Map
        final Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不需要身份认证就能访问的接口
        filterChainDefinitionMap.put("/test/**", DefaultFilter.anon.name());
        filterChainDefinitionMap.put("/captcha/**", DefaultFilter.anon.name());
        filterChainDefinitionMap.put("/sys-user-register/**", DefaultFilter.anon.name());
        filterChainDefinitionMap.put("/login/**", DefaultFilter.anon.name());
        // 其余所有接口都需要经过身份认证才能访问
        filterChainDefinitionMap.put("/**", DefaultFilter.authc.name());
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }

    /**
     * 注入 SecurityManager
     *
     * @author shiloh
     * @date 2023/6/11 19:35
     */
    @Bean
    @Primary
    public DefaultWebSecurityManager securityManager(SysUserService sysUserService) {
        final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(this.myShiroRealm(sysUserService));
        securityManager.setSessionManager(this.sessionManager());
        return securityManager;
    }

    /**
     * 注入会话管理器
     *
     * @author shiloh
     * @date 2023/6/11 20:00
     */
    @Bean
    public SessionManager sessionManager() {
        final MyShiroSessionManager myShiroSessionManager = new MyShiroSessionManager();
        // 设置全局会话超时时间，单位：毫秒
        myShiroSessionManager.setGlobalSessionTimeout(GLOBAL_SESSION_TIMEOUT_MS);
        // 开启会话定时校验
        myShiroSessionManager.setSessionValidationSchedulerEnabled(true);
        // 会话定时校验间隔，单位：毫秒
        myShiroSessionManager.setSessionValidationInterval(SESSION_VALIDATION_INTERVAL_MS);
        // 清除无效的 Session
        myShiroSessionManager.setDeleteInvalidSessions(true);
        return myShiroSessionManager;
    }

    /**
     * 注入 Shiro 生命周期 Bean 后置处理器
     *
     * @author shiloh
     * @date 2023/6/11 19:34
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启 Shiro 注解支持
     *
     * @author shiloh
     * @date 2023/6/11 19:37
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
