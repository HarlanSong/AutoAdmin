package cn.songhaiqing.tool.service;

import cn.songhaiqing.tool.base.BaseQuery;
import cn.songhaiqing.tool.base.BaseResponseList;
import cn.songhaiqing.tool.entity.Menu;
import cn.songhaiqing.tool.model.MenuViewModel;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface MenuService {

    BaseResponseList<MenuViewModel> getMenuPage(BaseQuery query);

    void addMenu(MenuViewModel menuViewModel);

    void editMenu(MenuViewModel menuViewModel);

    void deleteMenu(Long[] ids);

    Menu getMenu(long id);

    /**
     * 得到所有的一级菜单
     * @return
     */
    List<MenuViewModel> getParentMenu();

    /**
     * 得到用户可访问的菜单
     * @param userId
     * @return
     */
    List<MenuViewModel> getMenuByUserId(long userId);

    List<MenuViewModel> getAllMenuByRole(Long roleId);

}