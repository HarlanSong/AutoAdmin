package cn.songhaiqing.autoadmin.repository;

import cn.songhaiqing.autoadmin.entity.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SysUserRoleRepository extends JpaRepository<SysUserRole, Long>,JpaSpecificationExecutor<SysUserRole> {

    List<SysUserRole> findByUserId(long userId);

    List<SysUserRole> findByRoleId(long roleId);
}