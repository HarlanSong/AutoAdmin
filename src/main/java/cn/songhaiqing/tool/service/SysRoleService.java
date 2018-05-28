package cn.songhaiqing.tool.service;

import cn.songhaiqing.tool.base.BaseQuery;
import cn.songhaiqing.tool.base.BaseResponseList;
import cn.songhaiqing.tool.model.SysRoleViewModel;
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