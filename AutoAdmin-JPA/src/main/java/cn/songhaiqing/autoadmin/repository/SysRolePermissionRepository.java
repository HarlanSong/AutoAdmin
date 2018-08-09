package cn.songhaiqing.autoadmin.repository;

import cn.songhaiqing.autoadmin.entity.SysRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface SysRolePermissionRepository extends JpaRepository<SysRolePermission, Long>,JpaSpecificationExecutor<SysRolePermission> {
    List<SysRolePermission> findByRoleIdIn(List<Long> roleIds);

    List<SysRolePermission> findByRoleId(long roleId);
}