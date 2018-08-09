package cn.songhaiqing.autoadmin.controller.admin;

import cn.songhaiqing.autoadmin.annotation.AdminPermission;
import cn.songhaiqing.autoadmin.base.BaseQuery;
import cn.songhaiqing.autoadmin.base.BaseResponse;
import cn.songhaiqing.autoadmin.constants.AdminErrorMsg;
import cn.songhaiqing.autoadmin.base.BaseController;
import cn.songhaiqing.autoadmin.model.MenuViewModel;
import cn.songhaiqing.autoadmin.model.SysRoleViewModel;
import cn.songhaiqing.autoadmin.service.MenuService;
import cn.songhaiqing.autoadmin.service.SysRoleService;
import cn.songhaiqing.autoadmin.base.BaseResponseList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/admin/sysRole")
public class AdminSysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private MenuService menuService;

    @AdminPermission(menu = "/admin/sysRole/sysRoleView")
    @RequestMapping(value = "/sysRoleView")
    public ModelAndView roleView() {
        return new ModelAndView("/admin/sysRole");
    }

    @RequestMapping(value = "/getSysRolePage")
    public BaseResponseList getSysRolePage(BaseQuery query) {
        return sysRoleService.getSysRole(query);
    }


    @RequestMapping(value = "/addSysRoleView")
    public ModelAndView addRoleView() {
        ModelAndView modelAndView = new ModelAndView("/admin/sysRoleAdd");
        List<MenuViewModel> menuViewModels = menuService.getAllMenuByRole(null);
        modelAndView.addObject("menuViewModels", menuViewModels);
        return modelAndView;
    }

    @RequestMapping(value = "/updateSysRoleView")
    public ModelAndView updateSysRoleView(HttpServletRequest request, @RequestParam Long id) {
        SysRoleViewModel sysRole = sysRoleService.getSysRoleDetail(id);
        ModelAndView modelAndView = new ModelAndView("/admin/sysRoleUpdate");
        List<MenuViewModel> menuViewModels = menuService.getAllMenuByRole(id);
        modelAndView.addObject("sysRole", sysRole);
        modelAndView.addObject("menuViewModels", menuViewModels);
        return modelAndView;
    }

    @RequestMapping(value = "/addSysRole", method = RequestMethod.POST)
    public BaseResponse addSysRole(SysRoleViewModel model, Long[] menuIds) {
        if (menuIds == null || menuIds.length == 0) {
            return fail(AdminErrorMsg.PLEASE_CHOOSE_MENU);
        }
        sysRoleService.insert(model, menuIds);
        return success();
    }

    @RequestMapping(value = "/updateSysRole", method = RequestMethod.POST)
    public BaseResponse editSysRole(SysRoleViewModel model, @RequestParam Long[] menuIds) {
        if (menuIds == null || menuIds.length == 0) {
            return fail(AdminErrorMsg.PLEASE_CHOOSE_MENU);
        }
        sysRoleService.update(model, menuIds);
        return success();
    }

    @RequestMapping(value = "/deleteSysRole")
    public BaseResponse deleteSysRole( @RequestParam Long[] ids){
        sysRoleService.deleteSysRole(ids);
        return success();
    }
}
