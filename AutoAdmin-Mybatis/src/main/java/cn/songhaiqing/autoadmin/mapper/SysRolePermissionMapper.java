package cn.songhaiqing.autoadmin.mapper;

import cn.songhaiqing.autoadmin.base.BaseMapper;
import cn.songhaiqing.autoadmin.entity.SysRolePermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    List<SysRolePermission> findByRoleIdIn(@Param("roleIds") List<Long> roleIds);
}
