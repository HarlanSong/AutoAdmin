package cn.songhaiqing.autoadmin.controller.admin;

import cn.songhaiqing.autoadmin.annotation.AdminPermission;
import cn.songhaiqing.autoadmin.constants.AdminErrorMsg;
import cn.songhaiqing.autoadmin.base.BaseController;
import cn.songhaiqing.autoadmin.exception.AdminException;
import cn.songhaiqing.autoadmin.service.SysRoleService;
import cn.songhaiqing.autoadmin.service.SysUserService;
import cn.songhaiqing.autoadmin.base.BaseQuery;
import cn.songhaiqing.autoadmin.base.BaseResponse;
import cn.songhaiqing.autoadmin.base.BaseResponseList;
import cn.songhaiqing.autoadmin.utils.MD5Util;
import cn.songhaiqing.autoadmin.model.MenuViewModel;
import cn.songhaiqing.autoadmin.model.SysUserViewModel;
import cn.songhaiqing.autoadmin.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/admin/sysUser")
public class AdminSysUserController extends BaseController {


    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private SysRoleService sysRoleService;

    @AdminPermission(menu = "/admin/sysUser/sysUserView")
    @RequestMapping(value = "/sysUserView")
    public ModelAndView userView() {
        return new ModelAndView("/admin/sysUser");
    }

    @RequestMapping(value = "/getSysUserPage")
    public BaseResponseList getSysUserPage(BaseQuery query) {
        return sysUserService.getSysUserPage(query);
    }

    @RequestMapping(value = "/loginView")
    public ModelAndView loginView() {
        return new ModelAndView("/admin/login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse login(HttpServletRequest request, @RequestParam String account, @RequestParam String password) {
        SysUserViewModel user = sysUserService.login(account, new MD5Util().getMd5(password));
        if(user == null){
            return fail(AdminErrorMsg.ACCOUNT_OR_PASSWORD_WRONG);
        }

        List<MenuViewModel> menus = menuService.getMenuByUserId(user.getId());
        if(CollectionUtils.isEmpty(menus)){
            return fail(AdminErrorMsg.MENU_IS_EMPTY);
        }
        List<String> menuPermission = new ArrayList<>();
        for (MenuViewModel menu : menus) {
            if(!StringUtils.isEmpty(menu.getUrl())){
                menuPermission.add(menu.getUrl());
            }
            if(!CollectionUtils.isEmpty(menu.getMenus())){
                for (MenuViewModel menuViewModel : menu.getMenus()) {
                    if(!StringUtils.isEmpty(menuViewModel.getUrl())){
                        menuPermission.add(menuViewModel.getUrl());
                    }
                }
            }
        }
        request.getSession().setAttribute("userMenus", menus);
        request.getSession().setAttribute("menuPermission", menuPermission);
        setAdminUserInfo(request, user);
        return success(menus.get(0).getMenus().get(0).getUrl());
    }

    @RequestMapping(value = "/addSysUserView", method = RequestMethod.GET)
    public ModelAndView addUserView() {
        ModelAndView modelAndView = new ModelAndView("/admin/sysUserAdd");
        modelAndView.addObject("roles", sysRoleService.getAllRoles(null));
        return modelAndView;
    }

    @RequestMapping(value = "/editSysUserView", method = RequestMethod.GET)
    public ModelAndView editUserView(HttpServletRequest request, @RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView("/admin/sysUserEdit");
        SysUserViewModel userViewModel = sysUserService.getUserDetail(id);
        modelAndView.addObject("roles", sysRoleService.getAllRoles(id));
        modelAndView.addObject("userViewModel", userViewModel);
        return modelAndView;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("/admin/login");
    }

    @RequestMapping(value = "/addSysUser", method = RequestMethod.POST)
    public BaseResponse addUser(SysUserViewModel model, Long[] roleIds) {
        if (sysUserService.existAccount(model.getAccount())) {
            return fail(AdminErrorMsg.EXIST_ACCOUNT);
        }

        if (roleIds == null || roleIds.length == 0) {
            return fail(AdminErrorMsg.PLEASE_CHOOSE_SYS_USER_ROLE);
        }
        sysUserService.addUser(model, roleIds);
        return success();
    }

    @RequestMapping(value = "/editSysUser", method = RequestMethod.POST)
    public BaseResponse editUser(SysUserViewModel model, Long[] roleIds) {
        if (sysUserService.existAccount(model.getAccount(), model.getId())) {
            return fail(AdminErrorMsg.EXIST_ACCOUNT);
        }
        if (roleIds == null || roleIds.length == 0) {
            return fail(AdminErrorMsg.PLEASE_CHOOSE_SYS_USER_ROLE);
        }

        sysUserService.updateUser(model, roleIds);
        return success();
    }

    @RequestMapping(value = "/deleteSysUser")
    public BaseResponse deleteSysUser( @RequestParam Long[] ids){
        sysUserService.deleteSysUser(ids);
        return success();
    }

    @RequestMapping(value = "/changePasswordView", method = RequestMethod.GET)
    public ModelAndView changePasswordView(HttpServletRequest request) {
        return new ModelAndView("admin/changePassword");
    }

    @RequestMapping(value = "/changePassword")
    public BaseResponse changePassword(HttpServletRequest request,@RequestParam String oldPassword,
                                       @RequestParam String newPassword,@RequestParam String confirmNewPassword){
        if(!newPassword.equals(confirmNewPassword)){
            return fail(AdminErrorMsg.CONFIRM_PASSWORD_ERROR);
        }
        String errorMsg = null;
        try {
            sysUserService.changePassword(getAdminUser(request).getId(),oldPassword,newPassword);
        } catch (AdminException e) {
            errorMsg = e.getErrorMsg();
        }
        if(errorMsg != null){
            return fail(errorMsg);
        }
        request.getSession().invalidate();
        return success();
    }

}
