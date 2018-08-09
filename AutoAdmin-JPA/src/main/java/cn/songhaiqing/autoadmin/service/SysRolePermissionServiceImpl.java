package cn.songhaiqing.autoadmin.service;

import cn.songhaiqing.autoadmin.entity.SysRolePermission;
import cn.songhaiqing.autoadmin.repository.SysRolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class SysRolePermissionServiceImpl implements SysRolePermissionService {

    @Autowired
    private SysRolePermissionRepository sysRolePermissionRepository;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Transactional
    @Override
    public void addPermission(Long roleId, Long[] menusIds) {
        for (Long menusId : menusIds) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(roleId);
            sysRolePermission.setMenuId(menusId);
            sysRolePermissionRepository.save(sysRolePermission);
        }
    }

    @Override
    public void updatePermission(Long roleId, List<Long> menusIds) {
        List<Long> localMenuIds = new ArrayList<>();
        List<SysRolePermission> sysRolePermissions = sysRolePermissionRepository.findByRoleId(roleId);
        List<Long> deleteIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sysRolePermissions)) {
            for (SysRolePermission sysRolePermission : sysRolePermissions) {
                localMenuIds.add(sysRolePermission.getMenuId());
                if (!menusIds.contains(sysRolePermission.getMenuId())) {
                    deleteIds.add(sysRolePermission.getId());
                }
            }
        }
        List<Long> addMenuIds = new ArrayList<>();
        for (Long menuId : menusIds) {
            if (!localMenuIds.contains(menuId)) {
                addMenuIds.add(menuId);
            }
        }

        // 删除
        if (!deleteIds.isEmpty()) {
            for (Long deleteId : deleteIds) {
                sysRolePermissionRepository.delete(deleteId);
            }
        }
        //添加
        if (!addMenuIds.isEmpty()) {
            for (Long addMenuId : addMenuIds) {
                SysRolePermission sysRolePermission = new SysRolePermission();
                sysRolePermission.setRoleId(roleId);
                sysRolePermission.setMenuId(addMenuId);
                sysRolePermissionRepository.save(sysRolePermission);
            }

        }
    }

    @Override
    public List<Long> getMenuIdsByUser(long userId) {
        List<Long> menuIds = new ArrayList<>();
        List<Long> roleIds = sysUserRoleService.getRoleIdsByUser(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return menuIds;
        }
        List<SysRolePermission> sysRolePermissions = sysRolePermissionRepository.findByRoleIdIn(roleIds);
        if (CollectionUtils.isEmpty(sysRolePermissions)) {
            return menuIds;
        }
        for (SysRolePermission sysRolePermission : sysRolePermissions) {
            menuIds.add(sysRolePermission.getMenuId());
        }
        return menuIds;
    }

    @Override
    public List<Long> getMenuIdsByRole(long roleId) {
        List<Long> menuIds = new ArrayList<>();
        List<SysRolePermission> sysRolePermissions = sysRolePermissionRepository.findByRoleId(roleId);
        if (CollectionUtils.isEmpty(sysRolePermissions)) {
            return menuIds;
        }
        for (SysRolePermission sysRolePermission : sysRolePermissions) {
            menuIds.add(sysRolePermission.getMenuId());
        }
        return menuIds;
    }

    @Override
    public void deleteByRole(long roleId) {
        List<SysRolePermission> sysRolePermissions = sysRolePermissionRepository.findByRoleId(roleId);
        if (CollectionUtils.isEmpty(sysRolePermissions)) {
            return;
        }
        for (SysRolePermission sysRolePermission : sysRolePermissions) {
            sysRolePermission.setDeleted(true);
            sysRolePermission.setUpdateTime(new Date());
            sysRolePermissionRepository.save(sysRolePermission);
        }
    }
}
