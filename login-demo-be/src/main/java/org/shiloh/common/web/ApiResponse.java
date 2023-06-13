package org.shiloh.common.web;

import lombok.Data;
import org.shiloh.common.exception.CommonBizExceptionEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * 通用接口响应封装
 *
 * @param <T> 业务数据类型
 * @author shiloh
 * @date 2023/6/11 17:15
 */
@Data
public final class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = -7628974954024579058L;

    /**
     * 代表成功的响应码
     */
    public static final int SUCCESS_CODE = 0;
    /**
     * 代表成功的响应信息
     */
    public static final String SUCCESS_MSG = "操作成功";

    /**
     * 响应码，0 代表成功
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应时间：yyyy-MM-dd HH:mm:ss
     */
    private Date resTime;

    /**
     * 接口返回数据
     */
    private T data;

    public ApiResponse() {
        this.code = SUCCESS_CODE;
        this.msg = SUCCESS_MSG;
        this.resTime = new Date();
    }

    public ApiResponse(Integer code) {
        this.code = code;
        this.msg = SUCCESS_MSG;
        this.resTime = new Date();
    }

    public ApiResponse(String msg) {
        this.code = SUCCESS_CODE;
        this.msg = msg;
        this.resTime = new Date();
    }

    public ApiResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.resTime = new Date();
    }

    public ApiResponse(T data) {
        this.code = SUCCESS_CODE;
        this.msg = SUCCESS_MSG;
        this.resTime = new Date();
        this.data = data;
    }

    public ApiResponse(Integer code, T data) {
        this.code = code;
        this.msg = SUCCESS_MSG;
        this.resTime = new Date();
        this.data = data;
    }

    public ApiResponse(String msg, T data) {
        this.code = SUCCESS_CODE;
        this.msg = msg;
        this.resTime = new Date();
        this.data = data;
    }

    public ApiResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.resTime = new Date();
        this.data = data;
    }

    /**
     * 构建成功的响应数据
     *
     * @param <T> 业务数据类型
     * @return 响应数据实例
     * @author shiloh
     * @date 2023/6/11 17:18
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>();
    }

    /**
     * 构建成功的响应数据
     *
     * @param <T>  业务数据类型
     * @param code 响应码
     * @return 响应数据实例
     * @author shiloh
     * @date 2023/6/11 17:18
     */
    public static <T> ApiResponse<T> success(Integer code) {
        return new ApiResponse<>(code);
    }

    /**
     * 构建成功的响应数据
     *
     * @param <T> 业务数据类型
     * @param msg 响应信息
     * @return 响应数据实例
     * @author shiloh
     * @date 2023/6/11 17:18
     */
    public static <T> ApiResponse<T> success(String msg) {
        return new ApiResponse<>(msg);
    }

    /**
     * 构建成功的响应数据
     *
     * @param <T>  业务数据类型
     * @param data 业务数据
     * @return 响应数据实例
     * @author shiloh
     * @date 2023/6/11 17:18
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }

    /**
     * 构建成功的响应数据
     *
     * @param <T>  业务数据类型
     * @param code 响应信息
     * @param data 业务数据
     * @return 响应数据实例
     * @author shiloh
     * @date 2023/6/11 17:18
     */
    public static <T> ApiResponse<T> success(Integer code, T data) {
        return new ApiResponse<>(code, data);
    }

    /**
     * 构建成功的响应数据
     *
     * @param <T>  业务数据类型
     * @param msg  响应信息
     * @param data 业务数据
     * @return 响应数据实例
     * @author shiloh
     * @date 2023/6/11 17:18
     */
    public static <T> ApiResponse<T> success(String msg, T data) {
        return new ApiResponse<>(msg, data);
    }

    /**
     * 构建失败的响应数据
     *
     * @param <T> 业务数据类型
     * @author shiloh
     * @date 2023/6/11 17:40
     */
    public static <T> ApiResponse<T> error() {
        return new ApiResponse<>(
                CommonBizExceptionEnum.UNKNOWN_SERVER_ERROR.getErrCode(),
                CommonBizExceptionEnum.UNKNOWN_SERVER_ERROR.getErrMsg()
        );
    }

    /**
     * 构建失败的响应数据
     *
     * @param <T>  业务数据类型
     * @param code 响应码
     * @param msg  响应信息
     * @return 响应数据实例
     * @author shiloh
     * @date 2023/6/11 17:18
     */
    public static <T> ApiResponse<T> error(Integer code, String msg) {
        return new ApiResponse<>(code, msg);
    }

    /**
     * 构建失败的响应数据
     *
     * @param <T>  业务数据类型
     * @param msg  响应信息
     * @param data 业务数据
     * @return 响应数据实例
     * @author shiloh
     * @date 2023/6/11 17:18
     */
    public static <T> ApiResponse<T> error(String msg, T data) {
        final ApiResponse<T> apiResponse = new ApiResponse<>(msg, data);
        apiResponse.setCode(CommonBizExceptionEnum.UNKNOWN_SERVER_ERROR.getErrCode());
        return apiResponse;
    }

    /**
     * 构建失败的响应数据
     *
     * @param <T>  业务数据类型
     * @param code 响应码
     * @param data 业务数据
     * @return 响应数据实例
     * @author shiloh
     * @date 2023/6/11 17:18
     */
    public static <T> ApiResponse<T> error(Integer code, T data) {
        final ApiResponse<T> apiResponse = new ApiResponse<>(code, data);
        apiResponse.setMsg(CommonBizExceptionEnum.UNKNOWN_SERVER_ERROR.getErrMsg());
        return apiResponse;
    }


    /**
     * 构建失败的响应数据
     *
     * @param <T>  业务数据类型
     * @param code 响应码
     * @param msg  响应信息
     * @param data 业务数据
     * @return 响应数据实例
     * @author shiloh
     * @date 2023/6/11 17:18
     */
    public static <T> ApiResponse<T> error(Integer code, String msg, T data) {
        return new ApiResponse<>(code, msg, data);
    }
}
