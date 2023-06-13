package org.shiloh.security.realm;

import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.shiloh.entity.SysUser;
import org.shiloh.service.SysUserService;

/**
 * Shiro Realm
 *
 * @author shiloh
 * @date 2023/6/8 22:39
 */
@RequiredArgsConstructor
public class MyShiroRealm extends AuthorizingRealm {
    private final SysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 用户身份认证
     *
     * @param authenticationToken 身份认证 token
     * @return 身份认证信息
     * @throws AuthenticationException 身份认证失败抛出的异常
     * @author shiloh
     * @date 2023/6/12 23:10
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        final UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        final SysUser sysUser = this.sysUserService.getByUsername(token.getUsername());
        if (sysUser == null) {
            throw new UnknownAccountException();
        }

        return new SimpleAuthenticationInfo(
                sysUser,
                sysUser.getPassword(),
                ByteSource.Util.bytes(sysUser.getCredentialsSalt()),
                this.getName()
        );
    }
}
