package cn.songhaiqing.tool.service;

import cn.songhaiqing.tool.base.BaseQuery;
import cn.songhaiqing.tool.base.BaseResponseList;
import cn.songhaiqing.tool.model.SysRolePermissionViewModel;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface SysRolePermissionService {

    void insert (SysRolePermissionViewModel model);

    void update (SysRolePermissionViewModel model);

    SysRolePermissionViewModel getSysRolePermissionDetail(long id);

    BaseResponseList<SysRolePermissionViewModel> getSysRolePermission(BaseQuery query);

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
}
