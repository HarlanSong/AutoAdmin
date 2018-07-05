package cn.songhaiqing.autoadmin.service;

import cn.songhaiqing.autoadmin.base.BaseQuery;
import cn.songhaiqing.autoadmin.base.BaseResponseList;
import cn.songhaiqing.autoadmin.utils.DateUtil;
import cn.songhaiqing.autoadmin.entity.Menu;
import cn.songhaiqing.autoadmin.model.MenuViewModel;
import cn.songhaiqing.autoadmin.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Override
    public BaseResponseList<MenuViewModel> getMenuPage(BaseQuery query) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "orderNo"));
        Sort sort = new Sort(orders);
        Page<Menu> page = menuRepository.findAll(new Specification<Menu>() {
            @Override
            public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();
                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        },new PageRequest(query.getPage() - 1, query.getLimit(), sort));


        BaseResponseList<MenuViewModel> result = new BaseResponseList<>();
        result.setCount(page.getTotalElements());
        List<MenuViewModel> models = new ArrayList<>();
        for (Menu menu : page.getContent()) {
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
        if(menuViewModel.getOrderNo() != null) {
            menu.setOrderNo(menuViewModel.getOrderNo());
        }
        menuRepository.save(menu);
    }

    @Override
    public void editMenu(MenuViewModel menuViewModel) {
        Menu menu = menuRepository.findOne(menuViewModel.getId());
        if(menu == null) {
            return;
        }
        menu.setName(menuViewModel.getName());
        menu.setUrl(menuViewModel.getUrl());
        menu.setIcon(menuViewModel.getIcon());
        menu.setParentId(menuViewModel.getParentId());
        if(menuViewModel.getOrderNo() != null) {
            menu.setOrderNo(menuViewModel.getOrderNo());
        }
        menuRepository.save(menu);
    }

    @Override
    public void deleteMenu(Long[] ids) {
        for (Long id : ids) {
            Menu menu = menuRepository.findOne(id);
            menu.setDeleted(true);
            menuRepository.save(menu);
        }
    }

    @Override
    public Menu getMenu(long id) {
        return menuRepository.findOne(id);
    }

    @Override
    public List<MenuViewModel> getParentMenu() {
        List<MenuViewModel> models = new ArrayList<>();
        List<Menu> menus = menuRepository.findByParentIdOrderByOrderNo(0L);
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
        List<Long> menuIds = sysRolePermissionService.getMenuIdsByUser(userId);

        List<Menu> menus = menuRepository.findByParentIdOrderByOrderNo(0L);
        List<MenuViewModel> models = new ArrayList<>();
        for (Menu menu : menus) {
            if(!menuIds.contains(menu.getId())){
                continue;
            }
            MenuViewModel model = new MenuViewModel();
            model.setId(menu.getId());
            model.setName(menu.getName());
            model.setUrl(menu.getUrl());
            List<Menu> menuListTag =  menuRepository.findByParentIdOrderByOrderNo(menu.getId());
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
        List<Menu> menus = menuRepository.findByParentIdOrderByOrderNo(0L);
        List<MenuViewModel> models = new ArrayList<>();
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
            List<Menu> menuListTag =  menuRepository.findByParentIdOrderByOrderNo(menu.getId());
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
