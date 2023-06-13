package org.shiloh.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 通用业务异常枚举实现
 *
 * @author shiloh
 * @date 2023/6/11 13:58
 */
@Getter
@RequiredArgsConstructor
public enum CommonBizExceptionEnum implements BizExceptionEnum {
    /**
     * 未知异常
     */
    UNKNOWN_SERVER_ERROR(500, "未知异常，请稍后再试"),

    /**
     * 参数校验失败
     */
    INVALID_PARAMETER(400, "参数校验失败，请检查"),

    /**
     * 请求方法不支持
     */
    REQ_METHOD_NOT_SUPPORT(400, "请求方法【%s】不支持"),

    /**
     * 无效的访问令牌
     */
    INVALID_TOKEN(401, "invalid token");

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
