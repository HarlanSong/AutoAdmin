package cn.songhaiqing.autoadmin.service;

import cn.songhaiqing.autoadmin.base.BaseQuery;
import cn.songhaiqing.autoadmin.base.BaseResponseList;
import cn.songhaiqing.autoadmin.base.BaseServiceImpl;
import cn.songhaiqing.autoadmin.utils.DateUtil;
import cn.songhaiqing.autoadmin.entity.SysRole;

import cn.songhaiqing.autoadmin.model.SysRoleViewModel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    private SysUserRoleService sysUserRoleService;


    @Transactional
    @Override
    public void insert(SysRoleViewModel model, Long[] menuIds) {
        SysRole sysRole = new SysRole();
        sysRole.setId(model.getId());
        sysRole.setName(model.getName());
        sysRole.setDes(model.getDes());
        sysRole.setUpdateTime(new Date());
        sysRole.setCreateTime(new Date());
        sysRole.setDeleted(false);
        mapper.insert(sysRole);
        sysRolePermissionService.addPermission(sysRole.getId(), menuIds);
    }

    @Transactional
    @Override
    public void update(SysRoleViewModel model, Long[] menuIds) {
        SysRole sysRole = mapper.selectByPrimaryKey(model.getId());
        sysRole.setId(model.getId());
        sysRole.setName(model.getName());
        sysRole.setDes(model.getDes());
        sysRole.setUpdateTime(new Date());
        mapper.updateByPrimaryKey(sysRole);
        List<Long> ids = new ArrayList<>();
        for (Long menuId : menuIds) {
            ids.add(menuId);
        }
        sysRolePermissionService.updatePermission(model.getId(), ids);
    }

    @Override
    public SysRoleViewModel getSysRoleDetail(long id) {
        SysRole sysRole = mapper.selectByPrimaryKey(id);
        SysRoleViewModel model = new SysRoleViewModel();
        if(sysRole == null) {
            return model;
        }
        model.setId(sysRole.getId());
        model.setName(sysRole.getName());
        model.setDes(sysRole.getDes());
        model.setUpdateTime(DateUtil.dateToLongString(sysRole.getUpdateTime()));
        model.setCreateTime(DateUtil.dateToLongString(sysRole.getCreateTime()));
        return model;
    }

    @Override
    public BaseResponseList<SysRoleViewModel> getSysRole(BaseQuery query) {
        Page<SysRole> page  = PageHelper.startPage(query.getPage(), query.getLimit());
        mapper.findByPage();
        BaseResponseList<SysRoleViewModel> result = new BaseResponseList<>();
        result.setCount(page.getTotal());
        List<SysRoleViewModel> models = new ArrayList<>();
        List<SysRole> sysRoles = page.getResult();
        for (SysRole sysRole : sysRoles) {
            SysRoleViewModel model = new SysRoleViewModel();
            model.setId(sysRole.getId());
            model.setName(sysRole.getName());
            model.setDes(sysRole.getDes());
            model.setUpdateTime(DateUtil.dateToLongString(sysRole.getUpdateTime()));
            model.setCreateTime(DateUtil.dateToLongString(sysRole.getCreateTime()));
            models.add(model);
        }
        result.setData(models);
        return result;
    }

    @Override
    public List<SysRoleViewModel> getAllRoles(Long userId) {
        List<SysRoleViewModel> models = new ArrayList<>();
        SysRole params = new SysRole();
        params.setDeleted(false);
        List<SysRole> sysRoles = mapper.select(params);
        if(CollectionUtils.isEmpty(sysRoles)) {
            return models;
        }
        List<Long> roleIds = null;
        if(userId != null) {
            roleIds = sysUserRoleService.getRoleIdsByUser(userId);
        }
        for (SysRole sysRole : sysRoles) {
            SysRoleViewModel model = new SysRoleViewModel();
            model.setId(sysRole.getId());
            model.setName(sysRole.getName());
            model.setDes(sysRole.getDes());
            if(roleIds != null && roleIds.contains(sysRole.getId())) {
                model.setChecked(true);
            }else{
                model.setChecked(false);
            }
            models.add(model);
        }
        return models;
    }

    @Override
    public void deleteSysRole(Long[] ids) {
        for (Long id : ids) {
            SysRole sysRole = mapper.selectByPrimaryKey(id);
            sysRole.setDeleted(true);
            mapper.updateByPrimaryKey(sysRole);
            sysRolePermissionService.deleteByRole(id);
            sysUserRoleService.deleteByRole(id);
        }
    }
}
