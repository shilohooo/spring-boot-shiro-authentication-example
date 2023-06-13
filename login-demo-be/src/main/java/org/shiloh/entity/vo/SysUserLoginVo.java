package org.shiloh.entity.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 系统用户登录 Vo
 *
 * @author shiloh
 * @date 2023/6/12 22:57
 */
@Data
public class SysUserLoginVo implements Serializable {
    private static final long serialVersionUID = 3367411993899677596L;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Length(max = 50, message = "用户名不能超过50个字符")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(max = 50, message = "密码不能超过50个字符")
    private String password;

    /**
     * 验证码唯一标识
     */
    @NotBlank(message = "验证码唯一标识不能为空")
    private String captchaKey;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @Length(max = 5, message = "验证码不能超过5个字符")
    private String captcha;
}
