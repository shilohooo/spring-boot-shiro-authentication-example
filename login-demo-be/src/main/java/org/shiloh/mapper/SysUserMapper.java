package org.shiloh.mapper;

import org.mapstruct.*;
import org.shiloh.entity.SysUser;
import org.shiloh.entity.dto.SysUserDto;

/**
 * 系统用户信息 Mapper
 *
 * @author shiloh
 * @date 2023/6/11 13:26
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysUserMapper {
    /**
     * DTO 转实体
     *
     * @param sysUserDto 系统用户信息 DTO
     * @return 系统用户信息实体
     * @author shiloh
     * @date 2023/6/11 13:26
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "modifiedDate", ignore = true)
    })
    SysUser toEntity(SysUserDto sysUserDto);

    /**
     * 实体转 DTO
     *
     * @param sysUser 系统用户信息实体
     * @return 系统用户信息 DTO
     * @author shiloh
     * @date 2023/6/11 13:27
     */
    @Mappings(@Mapping(target = "password", ignore = true))
    SysUserDto toDto(SysUser sysUser);

    /**
     * 实体数据更新，值为空的字段不更新
     *
     * @param sysUser    系统用户信息实体
     * @param sysUserDto 系统用户信息 DTO
     * @return 系统用户信息实体
     * @author shiloh
     * @date 2023/6/11 13:27
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SysUser partialUpdate(SysUserDto sysUserDto, @MappingTarget SysUser sysUser);
}