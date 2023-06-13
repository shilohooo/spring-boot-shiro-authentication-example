package org.shiloh.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link org.shiloh.entity.SysUser}
 *
 * @author shiloh
 * @date 2023/6/11 13:18
 */
@Data
public class SysUserDto implements Serializable {
    private static final long serialVersionUID = -3438096879163156773L;

    /**
     * ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifiedDate;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Length(message = "用户名不能超过50个字符", max = 50)
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(message = "密码不能超过50个字符", max = 50)
    private String password;
}