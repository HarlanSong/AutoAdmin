<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>编辑角色</title>
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/css/base.css">
</head>
<body>
<div class="content-body">
    <form class="layui-form" action="">
        <input type="hidden" name="id" value="${sysRole.id}">

        <div class="layui-tab layui-tab-card">
            <ul class="layui-tab-title">
                <li class="layui-this">信息</li>
                <li>权限</li>
            </ul>
            <div class="layui-tab-content" style="height: 350px;">
                <div class="layui-tab-item layui-show">

                    <div class="layui-form-item">
                        <label class="layui-form-label">名称</label>
                        <div class="layui-input-block">
                            <input type="text" name="name" required lay-verify="required" autocomplete="off"
                                   class="layui-input" value="${sysRole.name}">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">描述</label>
                        <div class="layui-input-block">
                            <input type="text" name="des"  autocomplete="off"
                                   class="layui-input" value="${sysRole.des}">
                        </div>
                    </div>
                </div>
                <div class="layui-tab-item">

                    <c:forEach items="${menuViewModels}" var="menuViewModel">
                        <div class="layui-form-item">

                            <input type="checkbox" class="menu" value="${menuViewModel.id}"
                                   title="${menuViewModel.name} "
                                   <c:if test="${menuViewModel.checked}">checked</c:if>>

                            <c:if test="${not empty menuViewModel.menus}">
                                <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <c:forEach items="${menuViewModel.menus}" var="menuTag">

                                    <input type="checkbox" class="menu" value="${menuTag.id}" title="${menuTag.name}"
                                           <c:if test="${menuTag.checked}">checked</c:if> >

                                </c:forEach>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
            </div>
        </div>

    </form>
</div>

<script src="/layui/layui.js"></script>
<script>
    layui.use(['form', 'jquery', 'element'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var element = layui.element;
        form.on('submit(formDemo)', function (data) {
            var params = data.field;
            var checkedMenu = $("input:checkbox:checked");
            if(checkedMenu.length > 0) {
                var menuArray = [];
                checkedMenu.each(function () {
                    menuArray.push($(this).val());
                });
                params.menuIds = menuArray.join(",");
            }
            $.post("/admin/sysRole/updateSysRole",params, function (data) {
                if (data.code === 0) {
                    parent.layer.closeAll();
                    layer.msg('创建成功', {icon: 1});
                } else {
                    layer.alert(data.msg, {icon: 5});
                }
            }, "json");
            return false;
        });
    });
</script>
</body>
</html>
