package org.shiloh.controller;

import lombok.RequiredArgsConstructor;
import org.shiloh.common.web.ApiResponse;
import org.shiloh.entity.vo.SysUserLoginVo;
import org.shiloh.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * 登录相关接口
 *
 * @author shiloh
 * @date 2023/6/12 23:11
 */
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @Lazy})
public class LoginController {
    private final LoginService loginService;

    /**
     * 用户登录
     *
     * @param loginVo 登录参数
     * @return Token
     * @author shiloh
     * @date 2023/6/12 23:03
     */
    @PostMapping
    public ApiResponse<Serializable> login(@RequestBody @Valid SysUserLoginVo loginVo) {
        final Serializable token = this.loginService.login(loginVo);
        return ApiResponse.success(token);
    }
}
