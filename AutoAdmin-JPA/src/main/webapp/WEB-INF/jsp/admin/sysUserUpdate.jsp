<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>编辑系统用户</title>
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/css/base.css">
</head>
<body>
<div class="content-body">
    <form class="layui-form" action="">
        <input type="hidden" name="id" value="${userViewModel.id}">
        <div class="layui-form-item">
            <label class="layui-form-label">账号</label>
            <div class="layui-input-block">
                <input type="text" name="account" required lay-verify="required" autocomplete="off"
                       class="layui-input" value="${userViewModel.account}" readonly>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-block">
                <input type="text" name="name" required lay-verify="required" autocomplete="off"
                       class="layui-input" value="${userViewModel.name}">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">密码</label>
            <div class="layui-input-block">
                <input type="password" name="password"  autocomplete="off"
                       class="layui-input" placeholder="不为空时为重置密码">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">角色</label>
            <div class="layui-input-block">
                <c:forEach items="${roles}" var="role">
                    <input type="checkbox" value="${role.id}" title="${role.name}" <c:if test="${role.checked}">checked</c:if> >
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
        form.on('submit(formDemo)', function (data) {
            var params = data.field;
            var checkedMenu = $("input:checkbox:checked");
            if (checkedMenu.length > 0) {
                var menuArray = [];
                checkedMenu.each(function () {
                    menuArray.push($(this).val());
                });
                params.roleIds = menuArray.join(",");
            }
            $.post("/admin/sysUser/updateSysUser", params, function (data) {
                if (data.code === 0) {
                    parent.layer.closeAll();
                    layer.msg('编辑成功', {icon: 1});
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
