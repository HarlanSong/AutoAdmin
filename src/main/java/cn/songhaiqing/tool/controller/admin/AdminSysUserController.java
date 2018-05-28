package cn.songhaiqing.tool.controller.admin;

import cn.songhaiqing.tool.constants.AdminErrorMsg;
import cn.songhaiqing.tool.base.BaseController;
import cn.songhaiqing.tool.service.SysRoleService;
import cn.songhaiqing.tool.service.SysUserService;
import cn.songhaiqing.tool.base.BaseQuery;
import cn.songhaiqing.tool.base.BaseResponse;
import cn.songhaiqing.tool.base.BaseResponseList;
import cn.songhaiqing.tool.commons.MD5;
import cn.songhaiqing.tool.model.MenuViewModel;
import cn.songhaiqing.tool.model.SysUserViewModel;
import cn.songhaiqing.tool.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
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
        password = new MD5().getMd5(password);
        SysUserViewModel user = sysUserService.login(account, password);

        if(user == null){
            return fail(AdminErrorMsg.ACCOUNT_OR_PASSWORD_WRONG);
        }

        List<MenuViewModel> menus = menuService.getMenuByUserId(user.getId());
        if(CollectionUtils.isEmpty(menus)){
            return fail(AdminErrorMsg.MENU_IS_EMPTY);
        }
        request.getSession().setAttribute("userMenus", menus);
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

}
