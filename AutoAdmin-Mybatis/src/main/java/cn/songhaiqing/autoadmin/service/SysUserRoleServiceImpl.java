package cn.songhaiqing.autoadmin.service;

import cn.songhaiqing.autoadmin.entity.SysUserRole;
import cn.songhaiqing.autoadmin.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    
    @Override
    public List<Long> getRoleIdsByUser(long userId) {
        SysUserRole params = new SysUserRole();
        params.setUserId(userId);
        params.setDeleted(false);
        List<Long> ids = new ArrayList<>();
        List<SysUserRole> sysRoles = sysUserRoleMapper.select(params);
        if(CollectionUtils.isEmpty(sysRoles)){
            return ids;
        }
        for (SysUserRole sysUserRole : sysRoles) {
            ids.add(sysUserRole.getRoleId());
        }
        return ids;
    }

    @Transactional
    @Override
    public void addUserRole(long userId, Long[] roleIds) {
        for (Long roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUpdateTime(new Date());
            sysUserRole.setCreateTime(new Date());
            sysUserRole.setDeleted(false);
            sysUserRoleMapper.insert(sysUserRole);
        }
    }

    @Override
    public void updateUserRole(long userId, List<Long> roleIds) {
        List<Long> localRoleIds = new ArrayList<>();
        SysUserRole params = new SysUserRole();
        params.setUserId(userId);
        params.setDeleted(false);
        List<SysUserRole> sysRoles = sysUserRoleMapper.select(params);
        List<Long> deleteIds = new ArrayList<>();
        if(!CollectionUtils.isEmpty(sysRoles)){
            for (SysUserRole sysUserRole : sysRoles) {
                localRoleIds.add(sysUserRole.getRoleId());
                if(!roleIds.contains(sysUserRole.getRoleId())){
                    deleteIds.add(sysUserRole.getId());
                }
            }
        }
        List<Long> addRoleIds = new ArrayList<>();
        for (Long roleId : roleIds) {
            if(!localRoleIds.contains(roleId)){
                addRoleIds.add(roleId);
            }
        }

        // 删除
        if(!deleteIds.isEmpty()) {
            for (Long deleteId : deleteIds) {
                sysUserRoleMapper.deleteByPrimaryKey(deleteId);
            }
        }
        //添加
        if(!addRoleIds.isEmpty()) {
            for (Long addRoleId : addRoleIds) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(addRoleId);
                sysUserRole.setCreateTime(new Date());
                sysUserRole.setUpdateTime(new Date());
                sysUserRole.setDeleted(false);
                sysUserRoleMapper.insert(sysUserRole);
            }
        }
    }

    @Override
    public List<SysUserRole> getSysUserRole(long userId) {
        SysUserRole params = new SysUserRole();
        params.setUserId(userId);
        params.setDeleted(false);
        return sysUserRoleMapper.select(params);
    }

    @Override
    public void deleteByRole(long sysRoleId) {
        SysUserRole params = new SysUserRole();
        params.setDeleted(false);
        params.setRoleId(sysRoleId);
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.select(params);
        if(CollectionUtils.isEmpty(sysUserRoles)){
            return ;
        }
        for (SysUserRole sysUserRole : sysUserRoles) {
            sysUserRole.setDeleted(true);
            sysUserRole.setUpdateTime(new Date());
            sysUserRoleMapper.updateByPrimaryKey(sysUserRole);
        }
    }
}
