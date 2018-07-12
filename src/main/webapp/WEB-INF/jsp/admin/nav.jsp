<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="/layui/css/layui.css" media="all">
<link rel="stylesheet" href="/css/base.css">
<script src="/layui/layui.js"></script>
<div class="layui-header">
    <div class="layui-logo">AutoAdmin</div>
    <ul class="layui-nav layui-layout-left">
        <c:forEach items="${userMenus}" var="userMenu">
            <c:choose>
                <c:when test="${not empty userMenu.url}">
                    <li class="layui-nav-item"><a href="${userMenu.url}">${userMenu.name}</a>
                </c:when>
                <c:otherwise>
                    <li class="layui-nav-item"><a href="javascript:;">${userMenu.name}</a>
                </c:otherwise>
            </c:choose>


            <c:if test="${not empty userMenu.menus}">
                <dl class="layui-nav-child">
                    <c:forEach items="${userMenu.menus}" var="menuItem">
                        <dd><a href="${menuItem.url}">${menuItem.name}</a></dd>
                    </c:forEach>
                </dl>
            </c:if>
            </li>

        </c:forEach>

    </ul>
    <ul class="layui-nav layui-layout-right">
        <li class="layui-nav-item" style="margin-right: 20px;">
            <a href="javascript:;">${user.name}</a>
            <dl class="layui-nav-child">
                <dd><a href="/admin/sysUser/logout">退出</a></dd>
            </dl>
        </li>
    </ul>
</div>
<script>
    <c:if test="${empty userMenus}">
    location.href = "/admin/sysUser/loginView";
    </c:if>
    var urlName = document.location.pathname;
    var dd = document.getElementsByTagName("dd");
    var secondMenu = false;
    for (var i = 0; i < dd.length; i++) {
        var url = dd[i].getElementsByTagName("a")[0].getAttribute("href");
        if (url == urlName) {
            secondMenu = true;
            dd[i].setAttribute("class", "layui-this");
            var li = dd[i].parentNode.parentNode;
            var nameClass = li.getAttribute("class") + " layui-this";
            li.setAttribute("class", nameClass);
            break;
        }
    }

    if (!secondMenu) {
        var li = document.getElementsByTagName("li");
        for (var i = 0; i < li.length; i++) {
            var url = li[i].getElementsByTagName("a")[0].getAttribute("href");
            if (url == urlName) {
                li[i].setAttribute("class", "layui-this " + li[i].getAttribute("class"));
            }
        }
    }

</script>


