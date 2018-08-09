package cn.songhaiqing.autoadmin.service;

import cn.songhaiqing.autoadmin.base.BaseQuery;
import cn.songhaiqing.autoadmin.base.BaseResponseList;
import cn.songhaiqing.autoadmin.base.BaseServiceImpl;
import cn.songhaiqing.autoadmin.constants.AdminErrorMsg;
import cn.songhaiqing.autoadmin.exception.AdminException;
import cn.songhaiqing.autoadmin.utils.DateUtil;
import cn.songhaiqing.autoadmin.utils.MD5Util;
import cn.songhaiqing.autoadmin.entity.SysRole;
import cn.songhaiqing.autoadmin.entity.SysUser;
import cn.songhaiqing.autoadmin.entity.SysUserRole;
import cn.songhaiqing.autoadmin.mapper.SysUserMapper;
import cn.songhaiqing.autoadmin.model.SysUserViewModel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public SysUserViewModel login(String account, String password)  {
        SysUser params = new SysUser();
        params.setAccount(account);
        params.setDeleted(false);
        SysUser sysUser = sysUserMapper.selectOne(params);
        if(sysUser == null || !sysUser.getPassword().equals(MD5Util.getMd5(password + sysUser.getSalt()))){
            return null;
        }
        SysUserViewModel result = new SysUserViewModel();
        result.setId(sysUser.getId());
        result.setAccount(sysUser.getAccount());
        result.setName(sysUser.getName());
        return result;

    }

    @Override
    public BaseResponseList<SysUserViewModel> getSysUserPage(BaseQuery query) {
        Page<SysUser> page  = PageHelper.startPage(query.getPage(), query.getLimit());
        sysUserMapper.findByPage();
        BaseResponseList<SysUserViewModel> result = new BaseResponseList<>();
        result.setCount(page.getTotal());
        List<SysUserViewModel> models = new ArrayList<>();
        List<SysUser> sysUsers = page.getResult();
        for (SysUser sysUser : sysUsers) {
            SysUserViewModel model = new SysUserViewModel();
            model.setId(sysUser.getId());
            model.setAccount(sysUser.getAccount());
            model.setName(sysUser.getName());
            model.setUpdateTime(DateUtil.dateToLongString(sysUser.getUpdateTime()));
            model.setCreateTime(DateUtil.dateToLongString(sysUser.getCreateTime()));
            StringBuffer roleName = new StringBuffer();
            List<SysUserRole> sysUserRoles = sysUserRoleService.getSysUserRole(sysUser.getId());
            if(!CollectionUtils.isEmpty(sysUserRoles)) {
                for (SysUserRole sysUserRole : sysUserRoles) {
                    SysRole sysRole = sysRoleService.selectByKey(sysUserRole.getRoleId());
                    if(sysRole != null){
                        roleName.append(" " + sysRole.getName());
                    }
                }
                if(roleName.length() > 0){
                    model.setRoleName(roleName.substring(1));
                }
            }
            models.add(model);
        }
        result.setData(models);
        return result;
    }

    @Transactional
    @Override
    public void addUser(SysUserViewModel model, Long[] roleIds) {
        SysUser sysUser = new SysUser();
        sysUser.setAccount(model.getAccount());
        sysUser.setName(model.getName());
        String salt = UUID.randomUUID().toString();
        String password = MD5Util.getMd5(MD5Util.getMd5(model.getPassword()) + salt);
        sysUser.setSalt(salt);
        sysUser.setPassword(password);
        sysUser.setUpdateTime(new Date());
        sysUser.setCreateTime(new Date());
        sysUser.setDeleted(false);
        sysUserMapper.insert(sysUser);
        if(roleIds != null && roleIds.length > 0) {
            sysUserRoleService.addUserRole(sysUser.getId(), roleIds);
        }
    }

    @Transactional
    @Override
    public void updateUser(SysUserViewModel model, Long[] roleIds) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(model.getId());
        sysUser.setAccount(model.getAccount());
        sysUser.setName(model.getName());
        sysUser.setUpdateTime(new Date());
        if(!StringUtils.isEmpty(model.getPassword())){
            String salt = UUID.randomUUID().toString();
            String password = MD5Util.getMd5(MD5Util.getMd5(model.getPassword()) + salt);
            sysUser.setSalt(salt);
            sysUser.setPassword(password);
        }
        sysUserMapper.updateByPrimaryKey(sysUser);
        List<Long> roleArrayIds = new ArrayList<>();
        for (Long roleId : roleIds) {
            roleArrayIds.add(roleId);
        }
        sysUserRoleService.updateUserRole(model.getId(), roleArrayIds);
    }

    @Override
    public boolean existAccount(String account) {
        SysUser param =  new SysUser();
        param.setAccount(account);
        return sysUserMapper.selectCount(param) > 0;
    }

    @Override
    public boolean existAccount(String account, Long id) {
        return sysUserMapper.countByAccountAndIdNot(account, id) > 0;
    }

    @Override
    public SysUserViewModel getUserDetail(long id) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
        SysUserViewModel model = new SysUserViewModel();
        model.setId(sysUser.getId());
        model.setName(sysUser.getName());
        model.setAccount(sysUser.getAccount());
        return model;
    }

    @Override
    public void deleteSysUser(Long[] ids) {
        for (Long id : ids) {
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
            sysUser.setDeleted(true);
            sysUser.setUpdateTime(new Date());
            sysUserMapper.updateByPrimaryKey(sysUser);
        }
    }

    @Override
    public void changePassword(long id, String oldPassword, String newPassword) throws AdminException {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
        if(!sysUser.getPassword().equals(MD5Util.getMd5(MD5Util.getMd5(oldPassword) + sysUser.getSalt()))){
            throw new AdminException(AdminErrorMsg.OLD_PASSWORD_ERROR);
        }
        sysUser.setPassword(MD5Util.getMd5(MD5Util.getMd5(newPassword) + sysUser.getSalt()));
        sysUser.setUpdateTime(new Date());
        sysUserMapper.updateByPrimaryKey(sysUser);
    }
}
