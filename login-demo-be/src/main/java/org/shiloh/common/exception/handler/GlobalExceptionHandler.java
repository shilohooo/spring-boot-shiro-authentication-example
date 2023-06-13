package org.shiloh.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.shiloh.common.exception.BizException;
import org.shiloh.common.exception.CommonBizExceptionEnum;
import org.shiloh.common.web.ApiResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author shiloh
 * @date 2023/6/11 14:10
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 业务异常处理
     *
     * @param e 业务异常实例
     * @author shiloh
     * @date 2023/6/11 14:11
     */
    @ExceptionHandler(BizException.class)
    public ApiResponse<String> handleBizException(BizException e) {
        return ApiResponse.error(e.getErrCode(), e.getErrMsg());
    }

    /**
     * 处理参数校验异常
     *
     * @param e 异常实例
     * @author shiloh
     * @date 2023/6/11 18:01
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleParamValidException(MethodArgumentNotValidException e) {
        final BindingResult bindingResult = e.getBindingResult();
        final StringBuilder errMsg = new StringBuilder();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors()
                    .forEach(err -> errMsg.append(err.getDefaultMessage()).append("\n"));
        }
        if (StringUtils.isBlank(errMsg)) {
            errMsg.append(CommonBizExceptionEnum.INVALID_PARAMETER.getErrMsg());
        }
        return ApiResponse.error(CommonBizExceptionEnum.INVALID_PARAMETER.getErrCode(), errMsg.toString());
    }

    /**
     * 处理请求方式不支持异常
     *
     * @param e 异常示例
     * @author shiloh
     * @date 2023/6/11 18:19
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse<String> handleReqMethodNotSupportException(HttpRequestMethodNotSupportedException e) {
        return ApiResponse.error(
                CommonBizExceptionEnum.REQ_METHOD_NOT_SUPPORT.getErrCode(),
                String.format(CommonBizExceptionEnum.REQ_METHOD_NOT_SUPPORT.getErrMsg(), e.getMethod())
        );
    }

    /**
     * 处理其他异常
     *
     * @param e 异常实例
     * @author shiloh
     * @date 2023/6/11 14:14
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleException(Exception e) {
        log.error("未知异常：", e);
        return ApiResponse.error();
    }
}
