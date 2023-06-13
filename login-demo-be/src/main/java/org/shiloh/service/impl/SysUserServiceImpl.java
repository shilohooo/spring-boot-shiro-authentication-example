package org.shiloh.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shiloh.common.exception.BizException;
import org.shiloh.dao.SysUserDao;
import org.shiloh.entity.SysUser;
import org.shiloh.entity.dto.SysUserDto;
import org.shiloh.entity.dto.SysUserRegisterDto;
import org.shiloh.exception.RegisterBizExceptionEnum;
import org.shiloh.mapper.SysUserMapper;
import org.shiloh.security.codec.PasswordEncryptUtils;
import org.shiloh.service.CaptchaService;
import org.shiloh.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统用户信息 ServiceImpl
 *
 * @author shiloh
 * @date 2023/6/11 13:50
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @Lazy})
@Slf4j
public class SysUserServiceImpl implements SysUserService {
    private final SysUserDao sysUserDao;
    private final SysUserMapper sysUserMapper;
    private final CaptchaService captchaService;

    /**
     * 用户注册
     *
     * @param sysUserRegisterDto 系统用户信息注册 DTO
     * @return 系统用户信息实体
     * @author shiloh
     * @date 2023/6/11 13:49
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserDto register(SysUserRegisterDto sysUserRegisterDto) {
        // 验证码校验
        this.captchaService.verify(sysUserRegisterDto.getCaptchaKey(), sysUserRegisterDto.getCaptcha());

        if (this.sysUserDao.existsByUsername(sysUserRegisterDto.getUsername())) {
            throw new BizException(RegisterBizExceptionEnum.USERNAME_ALREADY_EXISTS);
        }
        if (!sysUserRegisterDto.getPassword().equals(sysUserRegisterDto.getConfirmPassword())) {
            throw new BizException(RegisterBizExceptionEnum.INCONSISTENCY_CONFIRM_PASSWORD);
        }

        SysUser sysUser = this.sysUserMapper.toEntity(sysUserRegisterDto);
        // 生成盐
        sysUser.setSalt(PasswordEncryptUtils.generateSalt());
        // 加密密码
        sysUser.setPassword(PasswordEncryptUtils.encrypt(sysUser.getPassword(), sysUser.getCredentialsSalt()));
        // 入库
        sysUser = this.sysUserDao.save(sysUser);
        return this.sysUserMapper.toDto(sysUser);
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 系统用户信息实体
     * @author shiloh
     * @date 2023/6/11 20:10
     */
    @Override
    public SysUser getByUsername(String username) {
        return this.sysUserDao.findByUsername(username);
    }
}
