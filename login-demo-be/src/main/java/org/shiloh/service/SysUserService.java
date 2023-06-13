package org.shiloh.service;

import org.shiloh.entity.SysUser;
import org.shiloh.entity.dto.SysUserDto;
import org.shiloh.entity.dto.SysUserRegisterDto;

/**
 * 系统用户信息 Service
 *
 * @author shiloh
 * @date 2023/6/11 13:10
 */
public interface SysUserService {
    /**
     * 用户注册
     *
     * @param sysUserRegisterDto 系统用户信息注册 DTO
     * @return 系统用户信息实体
     * @author shiloh
     * @date 2023/6/11 13:49
     */
    SysUserDto register(SysUserRegisterDto sysUserRegisterDto);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 系统用户信息实体
     * @author shiloh
     * @date 2023/6/11 20:10
     */
    SysUser getByUsername(String username);
}
