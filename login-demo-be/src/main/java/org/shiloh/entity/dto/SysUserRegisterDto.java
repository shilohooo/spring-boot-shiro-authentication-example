package org.shiloh.entity.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 系统用户信息注册 Dto
 *
 * @author shiloh
 * @date 2023/6/11 18:24
 */
@Setter
@Getter
public class SysUserRegisterDto extends SysUserDto {
    private static final long serialVersionUID = -4258232863569537395L;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    @Length(max = 50, message = "确认密码不能超过50个字符")
    private String confirmPassword;

    /**
     * 验证码唯一标识
     */
    @NotBlank(message = "验证码唯一标识不能为空")
    private String captchaKey;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String captcha;
}
