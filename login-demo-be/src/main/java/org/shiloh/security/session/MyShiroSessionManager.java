package org.shiloh.security.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 自定义 Shiro 会话管理器
 * <p>
 * 传统结构项目中，shiro从cookie中读取sessionId以此来维持会话，在前后端分离的项目中，
 * 我们选择在ajax的请求头中传递sessionId，因此需要重写shiro获取sessionId的方式
 *
 * @author shiloh
 * @date 2023/6/11 19:57
 */
@Slf4j
public class MyShiroSessionManager extends DefaultWebSessionManager {
    /**
     * token 在请求头中的 key 名称
     */
    public static final String AUTHORIZATION = "token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public MyShiroSessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String sessionId = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        // 如果请求头中有 Authorization 则其值为sessionId
        if (StringUtils.isNotBlank(sessionId)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        }
        // 否则按默认规则从cookie取sessionId
        return super.getSessionId(request, response);
    }

}
