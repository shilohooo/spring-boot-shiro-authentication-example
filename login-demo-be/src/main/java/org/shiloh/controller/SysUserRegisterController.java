package org.shiloh.controller;

import lombok.RequiredArgsConstructor;
import org.shiloh.common.web.ApiResponse;
import org.shiloh.entity.dto.SysUserDto;
import org.shiloh.entity.dto.SysUserRegisterDto;
import org.shiloh.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 系统用户注册接口
 *
 * @author shiloh
 * @date 2023/6/11 17:55
 */
@RestController
@RequestMapping("/sys-user-register")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @Lazy})
public class SysUserRegisterController {
    private final SysUserService sysUserService;

    /**
     * 用户注册
     *
     * @param sysUserRegisterDto 系统用户信息注册 DTO
     * @return 注册成功的用户信息
     * @author shiloh
     * @date 2023/6/11 17:57
     */
    @PostMapping
    public ApiResponse<SysUserDto> register(@RequestBody @Valid SysUserRegisterDto sysUserRegisterDto) {
        final SysUserDto sysUserDto = this.sysUserService.register(sysUserRegisterDto);
        return ApiResponse.success(sysUserDto);
    }
}
