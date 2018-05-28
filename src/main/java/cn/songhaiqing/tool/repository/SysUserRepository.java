package cn.songhaiqing.tool.repository;

import cn.songhaiqing.tool.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long>,JpaSpecificationExecutor<SysUser> {
    SysUser findByAccount(String account);

    long countByAccount(String account);

    long countByAccountAndIdNot(String account, Long id);
}