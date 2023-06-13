package org.shiloh.controller;

import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.shiloh.common.web.ApiResponse;
import org.shiloh.entity.SysUser;
import org.shiloh.entity.dto.SysUserDto;
import org.shiloh.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户信息接口
 *
 * @author shiloh
 * @date 2023/6/12 23:28
 */
@RestController
@RequestMapping("/sys-user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @Lazy})
public class SysUserController {
    private final SysUserMapper sysUserMapper;

    /**
     * 获取当前登录用户信息
     *
     * @return 系统用户信息 DTO
     * @author shiloh
     * @date 2023/6/12 23:30
     */
    @GetMapping("/current")
    public ApiResponse<SysUserDto> getCurrentUser() {
        final SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return ApiResponse.success(this.sysUserMapper.toDto(sysUser));
    }
}
