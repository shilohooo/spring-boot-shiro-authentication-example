package org.shiloh.dao;

import org.shiloh.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 系统用户信息 DAO
 *
 * @author shiloh
 * @date 2023/6/11 13:09
 */
public interface SysUserDao extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser> {
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 系统用户信息实体
     * @author shiloh
     * @date 2023/6/11 20:12
     */
    SysUser findByUsername(String username);

    /**
     * 根据用户名查询用户是否存在
     *
     * @param username 用户名
     * @return 如果用户已存在则返回 {@code true}，否则返回 {@code false}
     * @author shiloh
     * @date 2023/6/11 17:51
     */
    Boolean existsByUsername(String username);

}