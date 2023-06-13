package org.shiloh.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.shiloh.common.exception.BizExceptionEnum;

/**
 * 注册模块业务异常
 *
 * @author shiloh
 * @date 2023/6/11 17:44
 */
@Getter
@RequiredArgsConstructor
public enum RegisterBizExceptionEnum implements BizExceptionEnum {
    /**
     * 用户名已存在
     */
    USERNAME_ALREADY_EXISTS(10001, "用户名已存在"),

    /**
     * 密码不合法，密码至少需要包含一位大小写字母、特殊字符、数字
     */
    INVALID_PASSWORD(10002, "密码不合法，至少需要包含一位大小写字母、数字以及特殊字符"),

    /**
     * 确认密码和密码不一致
     */
    INCONSISTENCY_CONFIRM_PASSWORD(10003, "确认密码和密码不一致，请重新输入");

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
