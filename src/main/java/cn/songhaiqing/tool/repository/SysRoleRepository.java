package cn.songhaiqing.tool.repository;

import cn.songhaiqing.tool.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, Long>,JpaSpecificationExecutor<SysRole> {

}