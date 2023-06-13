package org.shiloh.exception;

import lombok.RequiredArgsConstructor;
import org.shiloh.common.exception.BizExceptionEnum;

/**
 * 登录相关业务异常枚举
 *
 * @author shiloh
 * @date 2023/6/11 20:14
 */
@RequiredArgsConstructor
public enum LoginBizException implements BizExceptionEnum {
    /**
     * 用户名不存在
     */
    UNKNOWN_ACCOUNT(30001, "用户名【%s】不存在"),
    /**
     * 用户名或密码错误
     */
    INCORRECT_CREDENTIALS(30002, "用户名或密码错误，请重试");

    /**
     * 错误码
     */
    private final Integer errCode;

    /**
     * 错误信息
     */
    private final String errMsg;

    /**
     * 获取错误码
     *
     * @return 错误码
     * @author shiloh
     * @date 2023/6/11 13:57
     */
    @Override
    public Integer getErrCode() {
        return this.errCode;
    }

    /**
     * 获取错误信息
     *
     * @return 错误信息
     * @author shiloh
     * @date 2023/6/11 13:57
     */
    @Override
    public String getErrMsg() {
        return this.errMsg;
    }
}
