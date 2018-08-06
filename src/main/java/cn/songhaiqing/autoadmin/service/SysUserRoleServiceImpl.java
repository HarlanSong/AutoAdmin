package cn.songhaiqing.autoadmin.service;

import cn.songhaiqing.autoadmin.entity.SysUserRole;
import cn.songhaiqing.autoadmin.repository.SysUserRoleRepository;
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
    private SysUserRoleRepository sysUserRoleRepository;

    @Override
    public List<Long> getRoleIdsByUser(long userId) {
        List<Long> ids = new ArrayList<>();
        List<SysUserRole> sysRoles = sysUserRoleRepository.findByUserId(userId);
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
            sysUserRoleRepository.save(sysUserRole);
        }
    }

    @Override
    public void updateUserRole(long userId, List<Long> roleIds) {
        List<Long> localRoleIds = new ArrayList<>();
        List<SysUserRole> sysRoles = sysUserRoleRepository.findByUserId(userId);
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
                sysUserRoleRepository.delete(deleteId);
            }
        }
        //添加
        if(!addRoleIds.isEmpty()) {
            for (Long addRoleId : addRoleIds) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(addRoleId);
                sysUserRoleRepository.save(sysUserRole);
            }

        }
    }

    @Override
    public void deleteByRole(long roleId) {
        List<SysUserRole> sysUserRoles = sysUserRoleRepository.findByRoleId(roleId);
        if(CollectionUtils.isEmpty(sysUserRoles)){
            return ;
        }
        for (SysUserRole sysUserRole : sysUserRoles) {
            sysUserRole.setDeleted(true);
            sysUserRole.setUpdateTime(new Date());
            sysUserRoleRepository.save(sysUserRole);
        }
    }

    @Override
    public List<SysUserRole> getSysUserRolesByUser(long userId) {
        return sysUserRoleRepository.findByUserId(userId);
    }
}
