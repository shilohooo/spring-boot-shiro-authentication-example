package org.shiloh.entity;

import lombok.*;
import org.shiloh.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * 系统用户信息实体
 *
 * @author shiloh
 * @date 2023/6/11 13:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "sys_user")
@org.hibernate.annotations.Table(appliesTo = "sys_user", comment = "系统用户信息")
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = -8024140073649281128L;

    /**
     * 用户名
     */
    @Column(name = "username", columnDefinition = "varchar(50) not null unique comment '用户名'")
    private String username;

    /**
     * 密码
     */
    @Column(name = "password", columnDefinition = "varchar(1000) not null comment '密码'")
    private String password;

    /**
     * 密码盐
     */
    @Column(name = "salt", columnDefinition = "varchar(255) not null comment '密码盐'")
    private String salt;

    /**
     * 获取用于密码加密的盐
     *
     * @return 用于密码加密的盐
     * @author shiloh
     * @date 2023/6/11 17:53
     */
    public String getCredentialsSalt() {
        return this.username + this.salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final SysUser sysUser = (SysUser) o;
        return Objects.equals(this.username, sysUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.username);
    }
}