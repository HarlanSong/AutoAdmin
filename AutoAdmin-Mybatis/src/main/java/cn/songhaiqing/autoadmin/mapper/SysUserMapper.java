package cn.songhaiqing.autoadmin.mapper;

import cn.songhaiqing.autoadmin.base.BaseMapper;
import cn.songhaiqing.autoadmin.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    long countByAccountAndIdNot(@Param("account") String account,@Param("id") long id);
}
