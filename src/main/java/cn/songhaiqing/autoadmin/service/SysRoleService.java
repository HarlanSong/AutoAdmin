package cn.songhaiqing.autoadmin.service;

import cn.songhaiqing.autoadmin.base.BaseQuery;
import cn.songhaiqing.autoadmin.base.BaseResponseList;
import cn.songhaiqing.autoadmin.model.SysRoleViewModel;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface SysRoleService {

    void insert (SysRoleViewModel model, Long[] menuIds);

    void update (SysRoleViewModel model, Long[] menuIds);

    SysRoleViewModel getSysRoleDetail(long id);

    BaseResponseList<SysRoleViewModel> getSysRole(BaseQuery query);

    List<SysRoleViewModel> getAllRoles(Long userId);
}