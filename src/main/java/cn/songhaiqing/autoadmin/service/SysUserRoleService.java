package cn.songhaiqing.autoadmin.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysUserRoleService {
    /**
     * 得到用户角色ID
     *
     * @param userId
     * @return
     */
    List<Long> getRoleIdsByUser(long userId);

    /**
     * 添加用户角色
     *
     * @param userId
     * @param roleIds
     */
    void addUserRole(long userId, Long[] roleIds);

    /**
     * 更新用户角色
     *
     * @param userId
     * @param roleIds
     */
    void updateUserRole(long userId, List<Long> roleIds);

}
