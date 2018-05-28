<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加系统用户</title>
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/css/base.css">
</head>
<body>
<div class="content-body">
    <form class="layui-form" action="">
        <div class="layui-form-item">
            <label class="layui-form-label">账号</label>
            <div class="layui-input-block">
                <input type="text" name="account" required lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-block">
                <input type="text" name="name" required lay-verify="required" autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">密码</label>
            <div class="layui-input-block">
                <input type="password" name="password" required lay-verify="required" autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">角色</label>
            <div class="layui-input-block">
                <c:forEach items="${roles}" var="role">
                    <input type="checkbox" value="${role.id}" title="${role.name}">
                </c:forEach>
            </div>
        </div>
        <div>

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
    //Demo
    layui.use(['form', 'jquery', 'element'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var element = layui.element;
        //监听提交
        form.on('submit(formDemo)', function (data) {
            var params = data.field;
            var checkedMenu = $("input:checkbox:checked");
            if (checkedMenu.length > 0) {
                var menuArray = [];
                checkedMenu.each(function () {
                    menuArray.push($(this).val());
                })
                params.roleIds = menuArray.join(",");
            }
            $.post("/admin/user/addSysUser", params, function (data) {
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
