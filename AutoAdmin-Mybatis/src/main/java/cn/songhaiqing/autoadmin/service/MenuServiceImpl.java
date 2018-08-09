package cn.songhaiqing.autoadmin.service;

import cn.songhaiqing.autoadmin.base.BaseQuery;
import cn.songhaiqing.autoadmin.base.BaseResponseList;
import cn.songhaiqing.autoadmin.base.BaseServiceImpl;
import cn.songhaiqing.autoadmin.utils.DateUtil;
import cn.songhaiqing.autoadmin.entity.Menu;
import cn.songhaiqing.autoadmin.mapper.MenuMapper;
import cn.songhaiqing.autoadmin.model.MenuViewModel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Override
    public void testFindAll() {
        List<Menu> menuList= menuMapper.selectAll();
        if(!CollectionUtils.isEmpty(menuList)){
            for (Menu menu : menuList) {
                System.out.println("name:" + menu.getName());
            }
        }
    }


    @Override
    public BaseResponseList<MenuViewModel> getMenuPage(BaseQuery query) {
        //Example example = new Example(Menu.class);
        //Example.Criteria criteria = example.createCriteria();
       /* if (StringUtil.isNotEmpty(country.getCountryname())) {
            criteria.andLike("countryname", "%" + country.getCountryname() + "%");
        }*/
        //分页查询
        Page<Menu> menuPage  = PageHelper.startPage(query.getPage(), query.getLimit());
        List<Menu> menus = menuMapper.selectByPage();

        BaseResponseList<MenuViewModel> result = new BaseResponseList<>();
        result.setCount(menuPage.getTotal());
        List<MenuViewModel> models = new ArrayList<>();
        for (Menu menu : menus) {
            MenuViewModel model = new MenuViewModel();
            model.setId(menu.getId());
            model.setName(menu.getName());
            model.setUrl(menu.getUrl());
            model.setIcon(menu.getIcon());
            model.setParentId(menu.getParentId());
            model.setOrderNo(menu.getOrderNo());
            model.setUpdateTime(DateUtil.dateToLongString(menu.getUpdateTime()));
            model.setCreateTime(DateUtil.dateToLongString(menu.getCreateTime()));
            models.add(model);
        }
        result.setData(models);
        return result;
    }

    @Override
    public void addMenu(MenuViewModel menuViewModel) {
        Menu menu = new Menu();
        menu.setName(menuViewModel.getName());
        menu.setUrl(menuViewModel.getUrl());
        menu.setIcon(menuViewModel.getIcon());
        menu.setParentId(menuViewModel.getParentId());
        menu.setUpdateTime(new Date());
        menu.setCreateTime(new Date());
        menu.setDeleted(false);
        if(menuViewModel.getOrderNo() != null) {
            menu.setOrderNo(menuViewModel.getOrderNo());
        }else{
            menu.setOrderNo(0);
        }
        menuMapper.insert(menu);
    }

    @Override
    public void updateMenu(MenuViewModel menuViewModel) {
        Menu menu = menuMapper.selectByPrimaryKey(menuViewModel.getId());
        if(menu == null) {
            return;
        }
        menu.setName(menuViewModel.getName());
        menu.setUrl(menuViewModel.getUrl());
        menu.setIcon(menuViewModel.getIcon());
        menu.setParentId(menuViewModel.getParentId());
        menu.setUpdateTime(new Date());
        if(menuViewModel.getOrderNo() != null) {
            menu.setOrderNo(menuViewModel.getOrderNo());
        }else{
            menu.setOrderNo(0);
        }
        menuMapper.updateByPrimaryKey(menu);
    }

    @Override
    public void deleteMenu(Long[] ids) {
        for (Long id : ids) {
            Menu menu = menuMapper.selectByPrimaryKey(id);
            menu.setDeleted(true);
            menuMapper.updateByPrimaryKey(menu);
        }
    }

    @Override
    public Menu getMenu(long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<MenuViewModel> getParentMenu() {
        List<MenuViewModel> models = new ArrayList<>();
        List<Menu> menus = menuMapper.findByParentIdOrderByOrderNo(0L);
        for (Menu menu : menus) {
            MenuViewModel model = new MenuViewModel();
            model.setId(menu.getId());
            model.setName(menu.getName());
            model.setUrl(menu.getUrl());
            models.add(model);
        }
        return models;
    }

    @Override
    public List<MenuViewModel> getMenuByUserId(long userId) {
        List<MenuViewModel> models = new ArrayList<>();
        List<Long> menuIds = sysRolePermissionService.getMenuIdsByUser(userId);

        List<Menu> menus = menuMapper.findByParentIdOrderByOrderNo(0L);

        for (Menu menu : menus) {
            if(!menuIds.contains(menu.getId())){
                continue;
            }
            MenuViewModel model = new MenuViewModel();
            model.setId(menu.getId());
            model.setName(menu.getName());
            model.setUrl(menu.getUrl());
            List<Menu> menuListTag =  menuMapper.findByParentIdOrderByOrderNo(menu.getId());
            if(CollectionUtils.isEmpty(menuListTag)){
                models.add(model);
                continue;
            }
            List<MenuViewModel> modelListTag = new ArrayList<>();
            for (Menu menuTag : menuListTag) {
                if(!menuIds.contains(menuTag.getId())){
                    continue;
                }
                MenuViewModel  modelTag = new MenuViewModel();
                modelTag.setId(menuTag.getId());
                modelTag.setName(menuTag.getName());
                modelTag.setUrl(menuTag.getUrl());
                modelListTag.add(modelTag);
            }
            model.setMenus(modelListTag);
            models.add(model);
        }
        return models;
    }

    @Override
    public List<MenuViewModel> getAllMenuByRole(Long roleId) {
        List<MenuViewModel> models = new ArrayList<>();
        List<Menu> menus = menuMapper.findByParentIdOrderByOrderNo(0L);

        List<Long> permissionMenuIds = null;
        if(roleId != null){
            permissionMenuIds = sysRolePermissionService.getMenuIdsByRole(roleId);
        }
        for (Menu menu : menus) {
            MenuViewModel model = new MenuViewModel();
            model.setId(menu.getId());
            model.setName(menu.getName());
            model.setUrl(menu.getUrl());
            if(permissionMenuIds != null && permissionMenuIds.contains(menu.getId())){
                model.setChecked(true);
            }else{
                model.setChecked(false);
            }
            List<Menu> menuListTag =  menuMapper.findByParentIdOrderByOrderNo(menu.getId());
            if(CollectionUtils.isEmpty(menuListTag)){
                models.add(model);
                continue;
            }
            List<MenuViewModel> modelListTag = new ArrayList<>();
            for (Menu menuTag : menuListTag) {
                MenuViewModel  modelTag = new MenuViewModel();
                modelTag.setId(menuTag.getId());
                modelTag.setName(menuTag.getName());
                modelTag.setUrl(menuTag.getUrl());
                if(permissionMenuIds != null && permissionMenuIds.contains(modelTag.getId())){
                    modelTag.setChecked(true);
                }else{
                    modelTag.setChecked(false);
                }
                modelListTag.add(modelTag);
            }
            model.setMenus(modelListTag);
            models.add(model);
        }

        return models;
    }
}
