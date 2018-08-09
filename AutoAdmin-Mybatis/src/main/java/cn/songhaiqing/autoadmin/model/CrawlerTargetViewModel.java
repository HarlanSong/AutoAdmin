package cn.songhaiqing.autoadmin.model;

import cn.songhaiqing.autoadmin.base.BaseViewModel;

public class CrawlerTargetViewModel extends BaseViewModel {
    private String name;
    private String homeUrl;
    private String contentUrlRegex;
    private String bloggerUrlRegex;
    private Integer homeMaxPage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String getContentUrlRegex() {
        return contentUrlRegex;
    }

    public void setContentUrlRegex(String contentUrlRegex) {
        this.contentUrlRegex = contentUrlRegex;
    }

    public String getBloggerUrlRegex() {
        return bloggerUrlRegex;
    }

    public void setBloggerUrlRegex(String bloggerUrlRegex) {
        this.bloggerUrlRegex = bloggerUrlRegex;
    }

    public Integer getHomeMaxPage() {
        return homeMaxPage;
    }

    public void setHomeMaxPage(Integer homeMaxPage) {
        this.homeMaxPage = homeMaxPage;
    }
}
