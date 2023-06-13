package org.shiloh.service;

import org.shiloh.entity.vo.SysUserLoginVo;

import java.io.Serializable;

/**
 * 登录相关 Service
 *
 * @author shiloh
 * @date 2023/6/12 23:03
 */
public interface LoginService {
    /**
     * 用户登录
     *
     * @param loginVo 登录参数
     * @return Token
     * @author shiloh
     * @date 2023/6/12 23:03
     */
    Serializable login(SysUserLoginVo loginVo);
}
