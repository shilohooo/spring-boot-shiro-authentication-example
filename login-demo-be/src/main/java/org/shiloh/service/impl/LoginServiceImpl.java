package org.shiloh.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.shiloh.common.exception.BizException;
import org.shiloh.entity.vo.SysUserLoginVo;
import org.shiloh.exception.LoginBizException;
import org.shiloh.service.CaptchaService;
import org.shiloh.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 登录相关 ServiceImpl
 *
 * @author shiloh
 * @date 2023/6/12 23:03
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @Lazy})
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final CaptchaService captchaService;

    /**
     * 用户登录
     *
     * @param loginVo 登录参数
     * @return Token
     * @author shiloh
     * @date 2023/6/12 23:03
     */
    @Override
    public Serializable login(SysUserLoginVo loginVo) {
        log.info("用户登录，参数：{}", loginVo);
        this.captchaService.verify(loginVo.getCaptchaKey(), loginVo.getCaptcha());
        final UsernamePasswordToken token = new UsernamePasswordToken(loginVo.getUsername(), loginVo.getPassword());
        final Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            throw new BizException(
                    LoginBizException.UNKNOWN_ACCOUNT.getErrCode(),
                    String.format(LoginBizException.UNKNOWN_ACCOUNT.getErrMsg(), loginVo.getUsername())
            );
        } catch (IncorrectCredentialsException e) {
            throw new BizException(LoginBizException.INCORRECT_CREDENTIALS);
        }

        return subject.getSession().getId();
    }
}
