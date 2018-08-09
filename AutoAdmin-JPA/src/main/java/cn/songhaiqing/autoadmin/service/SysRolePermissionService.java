package cn.songhaiqing.autoadmin.service;

import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface SysRolePermissionService {

    /**
     * 给指定角色添加权限
     * @param roleId
     * @param menusIds
     */
    void addPermission(Long roleId, Long[] menusIds);


    void updatePermission(Long roleId, List<Long> menusIds);

    /**
     * 得到用户可访问的菜单ID
     * @param userId
     * @return
     */
    List<Long> getMenuIdsByUser(long userId);

    List<Long> getMenuIdsByRole(long roleId);

    void deleteByRole(long roleId);
}
