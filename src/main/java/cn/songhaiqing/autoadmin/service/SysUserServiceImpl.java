package cn.songhaiqing.autoadmin.service;

import cn.songhaiqing.autoadmin.base.BaseQuery;
import cn.songhaiqing.autoadmin.base.BaseResponseList;
import cn.songhaiqing.autoadmin.utils.DateUtil;
import cn.songhaiqing.autoadmin.utils.MD5Util;
import cn.songhaiqing.autoadmin.entity.SysUser;
import cn.songhaiqing.autoadmin.entity.SysUserRole;
import cn.songhaiqing.autoadmin.model.SysUserViewModel;
import cn.songhaiqing.autoadmin.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public SysUserViewModel login(String account, String password)  {
        SysUser sysUser = sysUserRepository.findByAccount(account);
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
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"id"));
        Sort sort = new Sort(orders);
        Page<SysUser> page = sysUserRepository.findAll(new Specification<SysUser>(){
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();
                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        },new PageRequest(query.getPage() - 1, query.getLimit(),sort));
        BaseResponseList<SysUserViewModel> result = new BaseResponseList<>();
        result.setCount(page.getTotalElements());
        List<SysUserViewModel> models = new ArrayList<>();
        for (SysUser sysUser : page.getContent()) {
            SysUserViewModel model = new SysUserViewModel();
            model.setId(sysUser.getId());
            model.setAccount(sysUser.getAccount());
            model.setName(sysUser.getName());
            model.setUpdateTime(DateUtil.dateToLongString(sysUser.getUpdateTime()));
            model.setCreateTime(DateUtil.dateToLongString(sysUser.getCreateTime()));
            StringBuffer roleName = new StringBuffer();
            List<SysUserRole> sysUserRoles = sysUserRoleService.getSysUserRolesByUser(sysUser.getId());
            if(!CollectionUtils.isEmpty(sysUserRoles)) {
                for (SysUserRole sysUserRole : sysUserRoles) {
                    roleName.append(" " + sysUserRole.getSysRole().getName());
                }
                model.setRoleName(roleName.substring(1));
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
        sysUserRepository.save(sysUser);
        if(roleIds != null && roleIds.length > 0) {
            sysUserRoleService.addUserRole(sysUser.getId(), roleIds);
        }
    }

    @Transactional
    @Override
    public void updateUser(SysUserViewModel model, Long[] roleIds) {
        SysUser sysUser = sysUserRepository.findOne(model.getId());
        sysUser.setAccount(model.getAccount());
        sysUser.setName(model.getName());
        sysUser.setUpdateTime(new Date());
        if(!StringUtils.isEmpty(model.getPassword())){
            String salt = UUID.randomUUID().toString();
            String password = MD5Util.getMd5(MD5Util.getMd5(model.getPassword()) + salt);
            sysUser.setSalt(salt);
            sysUser.setPassword(password);
        }
        sysUserRepository.save(sysUser);
        List<Long> roleArrayIds = new ArrayList<>();
        for (Long roleId : roleIds) {
            roleArrayIds.add(roleId);
        }
        sysUserRoleService.updateUserRole(model.getId(), roleArrayIds);
    }

    @Override
    public boolean existAccount(String account) {
        return sysUserRepository.countByAccount(account) > 0;
    }

    @Override
    public boolean existAccount(String account, Long id) {
        return sysUserRepository.countByAccountAndIdNot(account, id) > 0;
    }

    @Override
    public SysUserViewModel getUserDetail(long id) {
        SysUser sysUser = sysUserRepository.findOne(id);
        SysUserViewModel model = new SysUserViewModel();
        model.setId(sysUser.getId());
        model.setName(sysUser.getName());
        model.setAccount(sysUser.getAccount());
        return model;
    }

    @Override
    public void deleteSysUser(Long[] ids) {
        for (Long id : ids) {
            SysUser sysUser = sysUserRepository.findOne(id);
            sysUser.setDeleted(true);
            sysUserRepository.save(sysUser);
        }
    }
}
