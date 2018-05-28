package cn.songhaiqing.tool.entity;

import cn.songhaiqing.tool.base.BaseEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Entity
@Table(name = "sys_role_permission")
@Where(clause = "deleted=0")
public class SysRolePermission extends BaseEntity {
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "menu_id")
    private Long menuId;

    public Long getRoleId() {
        return roleId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    public Long getMenuId() {
        return menuId;
    }
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}