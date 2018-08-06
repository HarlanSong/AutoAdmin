<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>系统管理 | 修改用户密码</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <jsp:include page="nav.jsp"/>

    <div class="layui-body" style="left:0; padding: 15px;">
        <span class="layui-breadcrumb">
          <a>系统管理</a>
          <a><cite>修改密码</cite></a>
        </span>
        <form class="layui-form" action="" style="width: 400px;margin-top: 20px;">
            <div class="layui-form-item">
                <label class="layui-form-label">当前密码</label>
                <div class="layui-input-block">
                    <input type="password" name="oldPassword" required lay-verify="required" autocomplete="off"
                           class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">新密码</label>
                <div class="layui-input-block">
                    <input type="password" name="newPassword" required lay-verify="required" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">确认新密码</label>
                <div class="layui-input-block">
                    <input type="password" name="confirmNewPassword" required lay-verify="required" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="change-sys-user_password">立即提交</button>
                </div>
            </div>

        </form>
    </div>
</div>

<script>
    layui.use(['element', 'form', 'jquery'], function () {
        var form = layui.form;
        var element = layui.element;
        var $ = layui.jquery;
        form.on('submit(change-sys-user_password)', function (data) {
            $.post("/admin/sysUser/changePassword", data.field, function (data) {
                if (data.code === 0) {
                    layer.confirm('修改成功，现在去重新登录。', {
                        btn: ['好的']
                    }, function () {
                        window.location.href = "/admin/sysUser/loginView";
                    });
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