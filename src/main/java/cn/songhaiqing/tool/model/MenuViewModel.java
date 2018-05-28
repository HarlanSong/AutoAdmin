package cn.songhaiqing.tool.model;

import java.util.List;


public class MenuViewModel {
    private Long id;
    private String name;
    private String url;
    private String icon;
    private Long parentId;
    private Integer orderNo;
    private String updateTime;
    private String createTime;
    private Boolean checked;

    private List<MenuViewModel> menus;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<MenuViewModel> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuViewModel> menus) {
        this.menus = menus;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}
