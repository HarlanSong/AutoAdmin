package cn.songhaiqing.tool.service;

import cn.songhaiqing.tool.base.BaseQuery;
import cn.songhaiqing.tool.base.BaseResponseList;
import cn.songhaiqing.tool.commons.DateUtils;
import cn.songhaiqing.tool.commons.MD5;
import cn.songhaiqing.tool.entity.SysUser;
import cn.songhaiqing.tool.entity.SysUserRole;
import cn.songhaiqing.tool.model.SysUserViewModel;
import cn.songhaiqing.tool.repository.SysUserRepository;
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
        if(sysUser == null || !sysUser.getPassword().equals(MD5.getMd5(password + sysUser.getSalt()))){
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
                // TODO 示例
                /**
                 if (query.getType() != null) {
                 predicate.add(cb.equal(r.get("type").as(Integer.class), query.getType()));
                 }
                 */
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
            model.setUpdateTime(DateUtils.dateToLongString(sysUser.getUpdateTime()));
            model.setCreateTime(DateUtils.dateToLongString(sysUser.getCreateTime()));
            StringBuffer roleName = new StringBuffer();
            List<SysUserRole> sysUserRoles = sysUser.getSysUserRoles();
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
        String password = MD5.getMd5(MD5.getMd5(model.getPassword()) + salt);
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
            String password = MD5.getMd5(MD5.getMd5(model.getPassword()) + salt);
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
}
