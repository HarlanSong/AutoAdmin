package cn.songhaiqing.tool.entity;

import cn.songhaiqing.tool.base.BaseEntity;
import org.hibernate.annotations.Where;
import javax.persistence.*;


@Entity
@Table(name = "menu")
@Where(clause = "deleted=0")
public class Menu extends BaseEntity {
    @Column(name = "name")

    private String name;
    @Column(name = "url")

    private String url;
    @Column(name = "icon")

    private String icon;

    @Column(name = "parent_id")
    private Long parentId = 0L;

    @Column(name = "order_no")
    private Integer orderNo = 0;

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

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}