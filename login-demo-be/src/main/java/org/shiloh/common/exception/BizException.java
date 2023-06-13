package org.shiloh.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常
 *
 * @author shiloh
 * @date 2023/6/11 13:55
 */
@Setter
@Getter
public class BizException extends RuntimeException {
    private static final long serialVersionUID = -5408616161408089615L;

    private Integer errCode;

    private String errMsg;

    public BizException(BizExceptionEnum bizExceptionEnum) {
        super(bizExceptionEnum.getErrMsg());
        this.errCode = bizExceptionEnum.getErrCode();
        this.errMsg = bizExceptionEnum.getErrMsg();
    }

    public BizException(BizExceptionEnum bizExceptionEnum, Throwable cause) {
        super(cause);
        this.errCode = bizExceptionEnum.getErrCode();
        this.errMsg = bizExceptionEnum.getErrMsg();
    }

    public BizException(String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
    }

    public BizException(String errMsg, Throwable cause) {
        super(errMsg, cause);
        this.errMsg = errMsg;
    }

    public BizException(Integer errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
