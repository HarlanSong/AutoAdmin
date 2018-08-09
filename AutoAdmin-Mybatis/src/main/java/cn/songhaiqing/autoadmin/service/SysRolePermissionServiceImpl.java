package cn.songhaiqing.autoadmin.service;


import cn.songhaiqing.autoadmin.base.BaseQuery;
import cn.songhaiqing.autoadmin.base.BaseResponseList;
import cn.songhaiqing.autoadmin.entity.SysRolePermission;
import cn.songhaiqing.autoadmin.mapper.SysRolePermissionMapper;
import cn.songhaiqing.autoadmin.model.SysRolePermissionViewModel;
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
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public void insert(SysRolePermissionViewModel model) {

    }

    @Override
    public void update(SysRolePermissionViewModel model) {

    }

    @Override
    public SysRolePermissionViewModel getSysRolePermissionDetail(long id) {
        return null;
    }

    @Override
    public BaseResponseList<SysRolePermissionViewModel> getSysRolePermission(BaseQuery query) {
        return null;
    }

    @Transactional
    @Override
    public void addPermission(Long roleId, Long[] menusIds) {
        for (Long menusId : menusIds) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(roleId);
            sysRolePermission.setMenuId(menusId);
            sysRolePermission.setUpdateTime(new Date());
            sysRolePermission.setCreateTime(new Date());
            sysRolePermission.setDeleted(false);
            sysRolePermissionMapper.insert(sysRolePermission);
        }
    }

    @Override
    public void updatePermission(Long roleId, List<Long> menusIds) {
        List<Long> localMenuIds = new ArrayList<>();
        SysRolePermission params = new SysRolePermission();
        params.setRoleId(roleId);
        List<SysRolePermission> sysRolePermissions = sysRolePermissionMapper.select(params);
        List<Long> deleteIds = new ArrayList<>();
        if(!CollectionUtils.isEmpty(sysRolePermissions)){
            for (SysRolePermission sysRolePermission : sysRolePermissions) {
                localMenuIds.add(sysRolePermission.getMenuId());
                if(!menusIds.contains(sysRolePermission.getMenuId())){
                    deleteIds.add(sysRolePermission.getId());
                }
            }
        }
        List<Long> addMenuIds = new ArrayList<>();
        for (Long menuId : menusIds) {
            if(!localMenuIds.contains(menuId)){
                addMenuIds.add(menuId);
            }
        }

        // 删除
        if(!deleteIds.isEmpty()) {
            for (Long deleteId : deleteIds) {
                sysRolePermissionMapper.deleteByPrimaryKey(deleteId);
            }
        }
        //添加
        if(!addMenuIds.isEmpty()) {
            for (Long addMenuId : addMenuIds) {
                SysRolePermission  sysRolePermission = new SysRolePermission();
                sysRolePermission.setRoleId(roleId);
                sysRolePermission.setMenuId(addMenuId);
                sysRolePermission.setUpdateTime(new Date());
                sysRolePermission.setCreateTime(new Date());
                sysRolePermission.setDeleted(false);
                sysRolePermissionMapper.insert(sysRolePermission);
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
        List<SysRolePermission> sysRolePermissions = sysRolePermissionMapper.findByRoleIdIn(roleIds);
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
        SysRolePermission params = new SysRolePermission();
        params.setRoleId(roleId);
        params.setDeleted(false);
        List<SysRolePermission> sysRolePermissions = sysRolePermissionMapper.select(params);
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
        SysRolePermission params = new SysRolePermission();
        params.setDeleted(false);
        params.setRoleId(roleId);
        List<SysRolePermission> sysRolePermissions = sysRolePermissionMapper.select(params);
        if (CollectionUtils.isEmpty(sysRolePermissions)) {
            return;
        }
        for (SysRolePermission sysRolePermission : sysRolePermissions) {
            sysRolePermission.setDeleted(true);
            sysRolePermission.setUpdateTime(new Date());
            sysRolePermissionMapper.updateByPrimaryKey(sysRolePermission);
        }
    }
}
