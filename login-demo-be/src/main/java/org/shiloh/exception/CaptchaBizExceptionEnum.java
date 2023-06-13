package org.shiloh.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.shiloh.common.exception.BizExceptionEnum;

/**
 * 验证码相关业务异常枚举
 *
 * @author shiloh
 * @date 2023/6/11 18:32
 */
@Getter
@RequiredArgsConstructor
public enum CaptchaBizExceptionEnum implements BizExceptionEnum {
    /**
     * 验证码已过期
     */
    EXPIRED_CAPTCHA(20000, "验证码已过期，请重新获取"),

    /**
     * 验证码错误
     */
    INCORRECT_CAPTCHA(20001, "验证码错误，请重新输入");

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
