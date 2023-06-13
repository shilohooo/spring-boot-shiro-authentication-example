package org.shiloh.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.shiloh.common.exception.CommonBizExceptionEnum;
import org.shiloh.common.web.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 自定义 Shiro 身份认证过滤器
 *
 * @author shiloh
 * @date 2023/6/11 19:40
 */
@RequiredArgsConstructor
@Slf4j
public class MyAuthenticationFilter extends FormAuthenticationFilter {
    private final ObjectMapper objectMapper;

    /**
     * 前后端分离，前端发起ajax请求，如果请求头里面有自定义请求头，例如：token，浏览器会发起一个OPTIONS预请求
     * 由于OPTIONS预请求中没有token，导致到了后端被shiro拦截
     * 此方法就是当请求方法是OPTIONS时，直接放行
     *
     * @author shiloh
     * @date 2023/6/11 19:41
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 返回 true 表示允许访问
        final boolean allowed = super.isAccessAllowed(request, response, mappedValue);
        if (!allowed) {
            // 判断是否为 OPTIONS 请求
            final String reqMethod = WebUtils.toHttp(request).getMethod();
            if (HttpMethod.OPTIONS.name().equalsIgnoreCase(reqMethod)) {
                return true;
            }
        }
        return allowed;
    }

    /**
     * 拒绝访问后 Shiro 会重定向到登录页面，前后端分离项目页面跳转由前端控制，
     * 这里返回 JSON 数据格式到前端，然后让前端处理就行，注意前后端跨域问题
     *
     * @author shiloh
     * @date 2023/6/11 19:44
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 返回 true 表示拒绝访问
        final Subject subject = this.getSubject(request, response);
        if (subject.getPrincipal() == null) {
            final String origin = WebUtils.toHttp(request).getHeader(HttpHeaders.ORIGIN);
            final HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE.toString());
            final ApiResponse<String> apiResponse = ApiResponse.error(
                    CommonBizExceptionEnum.INVALID_TOKEN.getErrCode(),
                    CommonBizExceptionEnum.INVALID_TOKEN.getErrMsg()
            );
            final PrintWriter printWriter = httpServletResponse.getWriter();
            printWriter.print(this.objectMapper.writeValueAsString(apiResponse));
            printWriter.flush();
            return false;
        }
        return true;
    }
}
