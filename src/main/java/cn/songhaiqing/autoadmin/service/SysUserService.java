package cn.songhaiqing.autoadmin.service;

import cn.songhaiqing.autoadmin.base.BaseQuery;
import cn.songhaiqing.autoadmin.base.BaseResponseList;
import cn.songhaiqing.autoadmin.model.SysUserViewModel;
import org.springframework.stereotype.Service;


@Service
public interface SysUserService {

    SysUserViewModel login(String account, String password);

    BaseResponseList<SysUserViewModel> getSysUserPage(BaseQuery query);

    /**
     * 添加系统用户
     * @param model
     * @param roleIds
     */
    void addUser(SysUserViewModel model, Long[] roleIds);

    void updateUser(SysUserViewModel model, Long[] roleIds);

    /**
     * 账号是否存在
     * @param account
     * @return
     */
    boolean existAccount(String account);

    /**
     * 账号是否存在
     * @param account
     * @param id
     * @return
     */
    boolean existAccount(String account, Long id);

    SysUserViewModel getUserDetail(long id);


}
