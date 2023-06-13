package org.shiloh.common.exception;

/**
 * 业务异常枚举接口
 *
 * @author shiloh
 * @date 2023/6/11 13:56
 */
public interface BizExceptionEnum {
    /**
     * 获取错误码
     *
     * @return 错误码
     * @author shiloh
     * @date 2023/6/11 13:57
     */
    Integer getErrCode();

    /**
     * 获取错误信息
     *
     * @return 错误信息
     * @author shiloh
     * @date 2023/6/11 13:57
     */
    String getErrMsg();
}
